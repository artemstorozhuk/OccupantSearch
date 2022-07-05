import { PermIdentity } from '@mui/icons-material'
import { IconButton } from '@mui/material'
import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import CardMedia from '@mui/material/CardMedia'
import Typography from '@mui/material/Typography'
import Occupant from '../model/Occupant'

export interface OccupantCardProp {
    occupant: Occupant
}

export default function OccupantCard(props: OccupantCardProp) {
    return (
        <Card sx={{
            width: 400,
            height: 400,
            margin: 1
        }}>
            {props.occupant.faceImageUrls.length === 0 &&
                <IconButton style={{
                    width: '100%',
                }}>
                    <PermIdentity sx={{
                        width: 300,
                        height: 300,
                    }} />
                </IconButton>
            }
            {props.occupant.faceImageUrls.length > 0 &&
                <CardMedia
                    component='img'
                    image={props.occupant.faceImageUrls[0]}
                    style={{
                        height: '300px',
                        objectFit: 'contain',
                        width: '100%'
                    }}
                />
            }
            <CardContent>
                <Typography gutterBottom variant='h5' component='div'>
                    {props.occupant.person.firstname} {props.occupant.person.lastname}
                </Typography>
            </CardContent>
        </Card>
    )
}