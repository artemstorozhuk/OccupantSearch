import { Component, ReactNode } from 'react';
import { useParams } from 'react-router-dom';
import { getOccupantPosts } from '../../client/Client';
import Post from '../../model/Post';
import { PostComponent } from './PostComponent';

export interface OccupantComponentProps {
    name: string
}

export interface OccupantComponentState {
    posts: Array<Post>
}

export function OccupantComponent() {
    const { name } = useParams();
    return <OccupantComponentWrapper name={name!!} />
}

class OccupantComponentWrapper extends Component<OccupantComponentProps, OccupantComponentState> {
    state = {
        posts: []
    }
    componentDidMount() {
        getOccupantPosts(this.props.name, result => {
            result.sort((a, b) => (b.date || 0) - (a.date || 0))
            this.setState({ posts: result })
        })
    }

    render(): ReactNode {
        return <div
            style={{
                display: 'flex',
                flexWrap: 'wrap',
                justifyContent: 'space-evenly',
            }}>
            {
                this.state.posts.map((post, i) =>
                    <PostComponent
                        key={i}
                        post={post} />
                )
            }
        </div>
    }
}