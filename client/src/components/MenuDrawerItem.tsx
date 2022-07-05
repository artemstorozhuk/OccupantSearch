import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'

export interface MenuDrawerItemProps {
    itemKey: string,
    text: string,
    icon: JSX.Element,
    onClick: () => void
}

export default function MenuDrawerItem(props: MenuDrawerItemProps) {
    return (
        <ListItem disablePadding
            key={props.itemKey}
            onClick={props.onClick}>
            <ListItemButton>
                <ListItemIcon>
                    {props.icon}
                </ListItemIcon>
                <ListItemText primary={props.text} />
            </ListItemButton>
        </ListItem>
    )
}
