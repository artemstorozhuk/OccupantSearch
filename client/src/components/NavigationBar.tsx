import MenuIcon from '@mui/icons-material/Menu'
import SearchIcon from '@mui/icons-material/Search'
import { alpha, InputBase, styled } from '@mui/material'
import AppBar from '@mui/material/AppBar'
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import Toolbar from '@mui/material/Toolbar'
import { Component } from 'react'

const Search = styled('div')(({ theme }) => ({
  position: 'relative',
  borderRadius: theme.shape.borderRadius,
  backgroundColor: alpha(theme.palette.common.white, 0.15),
  '&:hover': {
    backgroundColor: alpha(theme.palette.common.white, 0.25),
  },
  marginRight: theme.spacing(2),
  marginLeft: 0,
  width: '100%',
  [theme.breakpoints.up('sm')]: {
    marginLeft: theme.spacing(3),
    width: 'auto',
  },
}))

const SearchIconWrapper = styled('div')(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: '100%',
  position: 'absolute',
  pointerEvents: 'none',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
}))

const StyledInputBase = styled(InputBase)(({ theme }) => ({
  color: 'inherit',
  '& .MuiInputBase-input': {
    padding: theme.spacing(1, 1, 1, 0),
    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: '20ch',
    },
  },
}))

export enum NavigationBarType {
  SEARCH,
  EMPTY,
}

export interface NavigationBarProps {
  onSearchInputChange: (text: string) => void
  onMenuClick: () => void
}

export interface NavigationBarState {
  type: NavigationBarType
}

export default class NavigationBar extends Component<NavigationBarProps, NavigationBarState> {
  state: NavigationBarState = {
    type: NavigationBarType.SEARCH
  }

  render() {
    return (
      <Box sx={{ flexGrow: 1 }}>
        <AppBar
          position='fixed'
          style={{
            zIndex: 100
          }}>
          <Toolbar>
            <IconButton
              onClick={() => this.props.onMenuClick()}
              size='large'
              edge='start'
              color='inherit'
              aria-label='menu'
              sx={{ mr: 2 }}
            >
              <MenuIcon />
            </IconButton>
            {this.state.type === NavigationBarType.SEARCH &&
              <Search>
                <SearchIconWrapper >
                  <SearchIcon />
                </SearchIconWrapper>
                <StyledInputBase
                  placeholder='Search…'
                  inputProps={{ 'aria-label': 'search' }}
                  onChange={(event: React.ChangeEvent<HTMLInputElement>) => this.props.onSearchInputChange(event.target.value)}
                />
              </Search>
            }
          </Toolbar>
        </AppBar>
      </Box>
    )
  }
}