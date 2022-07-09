import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { Card, CardActionArea, CardActions, CardContent, CardMedia, IconButton, Typography } from '@mui/material';
import moment from 'moment';
import Carousel from 'react-material-ui-carousel';
import Post from '../../model/Post';

export interface PostComponentProps {
    post: Post
}

export function PostComponent(props: PostComponentProps) {
    const url = `https://vk.com/wall${props.post.postId}`
    return <Card
        sx={{
            margin: 1,
            width: 400,
            height: 500
        }}
        style={{
            objectFit: 'contain',
        }}>
        {props.post.imageUrls.length > 0 &&
            <Carousel>
                {
                    props.post.imageUrls.map((item, i) =>
                        <CardActionArea
                            key={i}
                            href={url}>
                            <CardMedia
                                component='img'
                                height='200'
                                image={item}
                            />
                        </CardActionArea>
                    )
                }
            </Carousel>
        }

        <CardActionArea
            href={url}>
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
                        <div>{moment(new Date(Number(props.post.date * 1000))).format('DD-MM-YYYY')}</div>
                    </IconButton>
                }
            </CardActions>
            <CardContent>
                <Typography
                    variant='body2'
                    color='text.secondary'>
                    {props.post.text}
                </Typography>
            </CardContent>
        </CardActionArea>
    </Card>
}