import { FileDownload, Search, ShowChart } from '@mui/icons-material'
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft'
import Divider from '@mui/material/Divider'
import Drawer from '@mui/material/Drawer'
import IconButton from '@mui/material/IconButton'
import List from '@mui/material/List'
import { styled } from '@mui/material/styles'
import { Component } from 'react'
import { download } from '../client/Client'
import { ExportFormat } from '../client/ExportFormat'
import MenuDrawerItem from './MenuDrawerItem'

const drawerWidth = 240

export enum MenuDrawerOption {
    SEARCH,
    ANALYTICS,
}

export interface MenuDrawerProps {
    onOptionSelected: (option: MenuDrawerOption) => void
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

export default class MenuDrawer extends Component<MenuDrawerProps, MenuDrawerState> {
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
                        itemKey='analytics'
                        text='Analytics'
                        icon={<ShowChart />}
                        onClick={() => {
                            this.setState({ open: false })
                            this.props.onOptionSelected(MenuDrawerOption.ANALYTICS)
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
                </List>
            </Drawer>
        )
    }
}
