import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { Card, CardActionArea, CardActions, CardContent, CardMedia, IconButton, Typography } from '@mui/material';
import Carousel from 'react-material-ui-carousel';
import { formatDate } from '../../extensions/Date';
import Post from './Post';

export interface PostComponentProps {
    post: Post
}

export function PostComponent(props: PostComponentProps) {
    const url = `https://vk.com/wall${props.post.postId}`
    return <Card
        sx={{
            margin: 1,
            width: 400,
            maxHeight: 500
        }}>
        {props.post.imageUrls.length > 0 &&
            <Carousel>
                {
                    props.post.imageUrls.map((item, i) =>
                        <CardActionArea
                            key={i}
                            href={url}
                            target='_blank'
                            rel='noopener noreferrer'>
                            <CardMedia
                                component='img'
                                height='200'
                                image={item}
                                style={{
                                    objectFit: 'contain',
                                }}
                            />
                        </CardActionArea>
                    )
                }
            </Carousel>
        }

        <CardActionArea
            href={url}
            target='_blank'
            rel='noopener noreferrer'>
            <CardActions>
                {props.post.views != null &&
                    <IconButton>
                        <VisibilityIcon />
                        <div>{props.post.views}</div>
                    </IconButton>
                }
                {props.post.date != null &&
                    <IconButton>
                        <CalendarMonthIcon />
                        <div>{formatDate(props.post.date * 1000)}</div>
                    </IconButton>
                }
            </CardActions>
            <CardContent>
                <Typography
                    whiteSpace='pre-wrap'
                    variant='body2'
                    color='text.secondary'>
                    {props.post.text}
                </Typography>
            </CardContent>
        </CardActionArea>
    </Card>
}