import { Component, ReactNode } from "react";
import { getGroups } from "../../client/Client";
import Group from "../../model/Group";
import GroupCard from "./GroupCard";

export interface GroupListState {
    page: number,
    loading: boolean,
    groups: Array<Group>,
}

export default class GroupList extends Component<{}, GroupListState> {
    state: GroupListState = {
        page: 0,
        loading: false,
        groups: [],
    }

    componentDidMount() {
        this.load(0, [])
    }

    render(): ReactNode {
        return (
            this.state.groups.map((group, index) => <GroupCard group={group} key={index} />)
        )
    }

    load(page: number, groups: Array<Group>) {
        this.setState({ loading: true })
        getGroups(page, (result: Array<Group>) => {
            this.setState({
                loading: false,
                page: page + 1,
                groups: groups.concat(result),
            })
        })
    }
}