import { Component, createRef } from 'react'
import { getOccupants } from '../client/Client'
import Occupant from '../model/Occupant'
import OccupantsResponse from '../model/OccupantsResponse'
import OccupantCard from './OccupantCard'
import ScrollToast from './ScrollToast'

export interface OccupantsListState {
    query: string,
    page: number,
    loading: boolean,
    occupants: Array<Occupant>,
    foundCount: number,
}

export default class OccupantsList extends Component<{}, OccupantsListState> {
    state: OccupantsListState = {
        query: '',
        page: 0,
        loading: false,
        occupants: [],
        foundCount: 0,
    }

    private root = createRef<HTMLDivElement>()
    private scrollToast = createRef<ScrollToast>()

    private onScroll = () => {
        this.updateToast(this.state.foundCount)
        if (!this.state.loading &&
            this.state.occupants.length < this.state.foundCount &&
            this.root?.current != null &&
            window.scrollY + 2 * window.innerHeight > this.root.current.clientHeight) {
            this.load(this.state.query, this.state.page, this.state.occupants)
        }
    }

    componentDidMount() {
        this.load('', 0, [])
        document.addEventListener('scroll', this.onScroll)
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.onScroll)
    }

    render() {
        return (
            <>
                <ScrollToast
                    ref={this.scrollToast} />
                <div
                    ref={this.root}
                    style={{
                        display: 'flex',
                        flexWrap: 'wrap',
                        justifyContent: 'space-evenly',
                    }}>
                    {
                        this.state.occupants.map((occupant, index) => (
                            <OccupantCard occupant={occupant} key={index} />
                        ))
                    }
                </div>
            </>
        )
    }

    setQuery(text: string) {
        window.scrollTo(0, 0)
        this.load(text, 0, [])
    }

    private load(query: string, page: number, occupants: Array<Occupant>) {
        this.setState({ loading: true })
        getOccupants(query, page, (result: OccupantsResponse) => {
            this.setState({
                loading: false,
                query: query,
                page: page + 1,
                occupants: occupants.concat(result.occupants),
                foundCount: result.foundCount,
            })
            this.updateToast(result.foundCount)
        })
    }

    private updateToast(count: number) {
        if (this.root.current != null) {
            for (var i = 0; i < this.root.current.children.length; i++) {
                if (this.root.current.children[i].getBoundingClientRect().bottom >= 0) {
                    this.scrollToast.current?.show(`${i + 1} / ${count}`)
                    break
                }
            }
        } else {
            this.scrollToast.current?.hide()
        }
    }
}