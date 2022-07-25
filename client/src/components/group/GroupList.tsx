import { Component, createRef, ReactNode } from 'react';
import Group from './Group';
import GroupCard from './GroupCard';
import { getGroups } from './GroupClient';

export interface GroupListState {
    page: number,
    loading: boolean,
    groups: Array<Group>,
    hasMore: boolean
}

export default class GroupList extends Component<{}, GroupListState> {
    state: GroupListState = {
        page: 0,
        loading: false,
        groups: [],
        hasMore: true,
    }

    private root = createRef<HTMLDivElement>()

    private onScroll = () => {
        if (!this.state.loading &&
            this.state.hasMore &&
            this.root?.current != null &&
            window.scrollY + 4 * window.innerHeight > this.root.current.clientHeight) {
            this.load(this.state.page, this.state.groups)
        }
    }

    componentDidMount() {
        this.load(0, [])
        document.addEventListener('scroll', this.onScroll)
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.onScroll)
    }

    render(): ReactNode {
        return <div
            ref={this.root}>
            {this.state.groups.map((group, index) => <GroupCard group={group} key={index} />)}
        </div>
    }

    load(page: number, groups: Array<Group>) {
        this.setState({ loading: true })
        getGroups(page, (result: Array<Group>) => {
            this.setState({
                loading: false,
                page: page + 1,
                groups: groups.concat(result),
                hasMore: result.length > 0
            })
        })
    }
}