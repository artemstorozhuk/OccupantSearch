import { Component, createRef } from 'react'
import { getOccupants } from '../../client/Client'
import { formatDate } from '../../extensions/Date'
import Occupant from '../../model/Occupant'
import OccupantsResponse from '../../model/OccupantsResponse'
import FoundCountToast from './FoundCountToast'
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
    private foundCountToast = createRef<FoundCountToast>()

    private onScroll = () => {
        this.updateToast()
        if (!this.state.loading &&
            this.state.occupants.length < this.state.foundCount &&
            this.root?.current != null &&
            window.scrollY + 4 * window.innerHeight > this.root.current.clientHeight) {
            this.load(this.state.query, this.state.page, this.state.occupants, false)
        }
    }

    componentDidMount() {
        this.load('', 0, [], true)
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
                <FoundCountToast
                    ref={this.foundCountToast} />
            </>
        )
    }

    setQuery(text: string) {
        window.scrollTo(0, 0)
        this.load(text, 0, [], true)
    }

    load(query: string, page: number, occupants: Array<Occupant>, showFoundCound: boolean) {
        this.setState({ loading: true })
        getOccupants(query, page, (result: OccupantsResponse) => {
            this.setState({
                loading: false,
                query: query,
                page: page + 1,
                occupants: occupants.concat(result.occupants),
                foundCount: result.foundCount,
            })
            if (showFoundCound) {
                this.foundCountToast?.current?.show(`Found ${result.foundCount}`)
            }
            this.updateToast()
        })
    }

    updateToast() {
        if (this.root.current != null) {
            for (var i = 0; i < this.root.current.children.length; i++) {
                if (this.root.current.children[i].getBoundingClientRect().bottom >= 0) {
                    const date = formatDate(this.state.occupants[i].date * 1000)
                    this.scrollToast.current?.show(date)
                    break
                }
            }
        } else {
            this.scrollToast.current?.hide()
        }
    }
}