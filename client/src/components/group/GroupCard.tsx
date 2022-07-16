import { Avatar, CardActionArea, CardHeader } from '@mui/material'
import Card from '@mui/material/Card'
import Group from '../../model/Group'

export interface GroupCardProp {
    group: Group
}

export default function GroupCard(props: GroupCardProp) {
    return (
        <Card
            sx={{
                margin: 1,
            }}>
            <CardActionArea
                href={`https://vk.com${props.group.url}`}
                target='_blank'
                rel='noopener noreferrer'>
                <CardHeader
                    avatar={
                        <Avatar src={props.group.avatar} />
                    }
                    title={props.group.name}
                    subheader={`Posts count: ${props.group.count}`} />
            </CardActionArea>
        </Card>
    )
}