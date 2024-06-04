import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { AppBar, IconButton, Toolbar, Typography } from '@mui/material';
import { Component, ReactNode } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Post from './Post';
import { getOccupantPosts } from './PostClient';
import { PostComponent } from './PostComponent';

export interface OccupantComponentProps {
    name: string,
    onBackClick: () => void
}

export interface OccupantComponentState {
    posts: Array<Post>
}

export function OccupantComponent() {
    const { name } = useParams();
    const navigate = useNavigate();
    return <OccupantComponentWrapper
        name={name!!}
        onBackClick={() => navigate(-1)} />
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
        return <>
            <AppBar position='fixed'>
                <Toolbar>
                    <IconButton
                        size='large'
                        edge='start'
                        color='inherit'
                        aria-label='menu'
                        sx={{ mr: 2 }}
                        onClick={() => this.props.onBackClick()}>
                        <ArrowBackIcon />
                    </IconButton>
                    <Typography
                        variant='h6'
                        component='div'
                        sx={{ flexGrow: 1 }}>
                        {this.props.name}
                    </Typography>
                </Toolbar>
            </AppBar>
            <div
                style={{
                    marginTop: '60px',
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
        </>
    }
}