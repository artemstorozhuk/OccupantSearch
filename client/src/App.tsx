import { Component, createRef } from 'react'
import AnalyticsChart from './components/AnalyticsChart'
import MenuDrawer, { MenuDrawerOption } from './components/MenuDrawer'
import NavigationBar, { NavigationBarType } from './components/NavigationBar'
import OccupantsList from './components/OccupantsList'

interface AppState {
  option: MenuDrawerOption
}

export default class App extends Component<{}, AppState> {
  state: AppState = {
    option: MenuDrawerOption.SEARCH
  }

  render() {
    const menuDrawer = createRef<MenuDrawer>()
    const occupantsList = createRef<OccupantsList>()
    const navigationBar = createRef<NavigationBar>()
    return (
      <>
        <NavigationBar
          ref={navigationBar}
          onSearchInputChange={(text: string) => occupantsList.current?.setQuery(text)}
          onMenuClick={() => menuDrawer.current?.setState({ open: true })}
        />
        <MenuDrawer
          ref={menuDrawer}
          onOptionSelected={option => {
            this.setState({ option: option })
            switch (option) {
              case MenuDrawerOption.SEARCH:
                navigationBar?.current?.setState({ type: NavigationBarType.SEARCH })
                break;
              case MenuDrawerOption.ANALYTICS:
                navigationBar?.current?.setState({ type: NavigationBarType.EMPTY })
                break;
            }
          }}
        />
        <div
          style={{
            marginTop: '60px'
          }}>
          {this.state.option === MenuDrawerOption.SEARCH &&
            <OccupantsList
              ref={occupantsList}
            />
          }
          {this.state.option === MenuDrawerOption.ANALYTICS &&
            <AnalyticsChart />
          }
        </div>
      </>
    )
  }
}
