import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import CardMedia from '@mui/material/CardMedia'
import Typography from '@mui/material/Typography'
import { useNavigate } from 'react-router-dom'
import Occupant from '../../model/Occupant'

export interface OccupantCardProp {
    occupant: Occupant
}

export default function OccupantCard(props: OccupantCardProp) {
    const navigate = useNavigate()
    return (
        <Card
            sx={{
                width: 400,
                height: 400,
                margin: 1,
                cursor: 'pointer'
            }}
            onClick={() => navigate(`/occupant/${props.occupant.name}`)}>
            <CardMedia
                component='img'
                image={props.occupant.imageUrl != null ? props.occupant.imageUrl : 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Placeholder_no_text.svg/1200px-Placeholder_no_text.svg.png'}
                style={{
                    height: '300px',
                    objectFit: 'contain',
                    width: '100%'
                }}
            />
            <CardContent>
                <Typography gutterBottom variant='h5' component='div'>
                    {props.occupant.name}
                </Typography>
            </CardContent>
        </Card>
    )
}