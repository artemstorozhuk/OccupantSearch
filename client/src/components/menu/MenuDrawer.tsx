import { FileDownload, GitHub, Search, ShowChart } from '@mui/icons-material'
import AttachMoneyIcon from '@mui/icons-material/AttachMoney'
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft'
import DynamicFeedIcon from '@mui/icons-material/DynamicFeed'
import MapIcon from '@mui/icons-material/Map'
import Divider from '@mui/material/Divider'
import Drawer from '@mui/material/Drawer'
import IconButton from '@mui/material/IconButton'
import List from '@mui/material/List'
import { styled } from '@mui/material/styles'
import { Component } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { download } from '../../client/Client'
import { ExportFormat } from '../../client/ExportFormat'
import MenuDrawerItem from './MenuDrawerItem'
const drawerWidth = 240

export enum MenuDrawerOption {
    SEARCH,
    MAP,
    ANALYTICS,
    GROUPS,
}

export interface MenuDrawerProps {
    onOptionSelected: (option: MenuDrawerOption) => void,
    style: MenuDrawerOption,
}

export interface MenuDrawerState {
    open: boolean,
}

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
}))

export interface MenuDrawerWrapperProps {
    drawerRef: React.RefObject<MenuDrawer>
}

export function MenuDrawerWrapper(props: MenuDrawerWrapperProps) {
    const navigate = useNavigate()
    const location = useLocation()

    const style = location.pathname === 'analytics' ? MenuDrawerOption.ANALYTICS : MenuDrawerOption.SEARCH
    return <MenuDrawer
        ref={props.drawerRef}
        style={style}
        onOptionSelected={(option) => {
            switch (option) {
                case MenuDrawerOption.SEARCH: {
                    navigate('/')
                    break
                }
                case MenuDrawerOption.MAP: {
                    navigate('/map')
                    break
                }
                case MenuDrawerOption.ANALYTICS: {
                    navigate('/analytics')
                    break
                }
                case MenuDrawerOption.GROUPS: {
                    navigate('/groups')
                    break
                }
            }
        }}
    />
}

export class MenuDrawer extends Component<MenuDrawerProps, MenuDrawerState> {
    state: MenuDrawerState = {
        open: false
    }

    render() {
        return (
            <Drawer
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                    },
                }}
                variant='temporary'
                anchor='left'
                open={this.state.open}
            >
                <DrawerHeader>
                    <IconButton onClick={() => this.setState({ open: false })}>
                        <ChevronLeftIcon />
                    </IconButton>
                </DrawerHeader>
                <List>
                    <Divider />
                    <MenuDrawerItem
                        itemKey='search'
                        text='Search'
                        icon={<Search />}
                        onClick={() => {
                            this.setState({ open: false })
                            this.props.onOptionSelected(MenuDrawerOption.SEARCH)
                        }
                        }
                    />
                    <MenuDrawerItem
                        itemKey='map'
                        text='Map'
                        icon={<MapIcon />}
                        onClick={() => {
                            this.setState({ open: false })
                            this.props.onOptionSelected(MenuDrawerOption.MAP)
                        }}
                    />
                    <MenuDrawerItem
                        itemKey='analytics'
                        text='Analytics'
                        icon={<ShowChart />}
                        onClick={() => {
                            this.setState({ open: false })
                            this.props.onOptionSelected(MenuDrawerOption.ANALYTICS)
                        }}
                    />
                    <MenuDrawerItem
                        itemKey='groups'
                        text='Groups'
                        icon={<DynamicFeedIcon />}
                        onClick={() => {
                            this.setState({ open: false })
                            this.props.onOptionSelected(MenuDrawerOption.GROUPS)
                        }}
                    />
                    <Divider />
                    <MenuDrawerItem
                        itemKey='export json'
                        text='Export JSON'
                        icon={<FileDownload />}
                        onClick={() => {
                            this.setState({ open: false })
                            download(ExportFormat.JSON)
                        }}
                    />
                    <MenuDrawerItem
                        itemKey='export csv'
                        text='Export CSV'
                        icon={<FileDownload />}
                        onClick={() => {
                            this.setState({ open: false })
                            download(ExportFormat.CSV)
                        }}
                    />
                    <Divider />
                    <MenuDrawerItem
                        itemKey='donate'
                        text='Donate'
                        icon={<AttachMoneyIcon />}
                        onClick={() => {
                            this.setState({ open: false })
                            window.open('https://war.ukraine.ua/donate/')
                        }}
                    />
                    <MenuDrawerItem
                        itemKey='github'
                        text='Github Repo'
                        icon={<GitHub />}
                        onClick={() => {
                            this.setState({ open: false })
                            window.open('https://github.com/artemstorozhuk/OccupantSearch/')
                        }}
                    />
                </List>
            </Drawer>
        )
    }
}
