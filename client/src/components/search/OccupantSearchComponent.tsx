import { Component, createRef, ReactNode } from 'react';
import { MenuDrawer, MenuDrawerWrapper } from '../menu/MenuDrawer';
import NavigationBar, { NavigationBarType } from '../menu/NavigationBar';
import OccupantsList from './OccupantsList';

export class OccupantSearchComponent extends Component {
    render(): ReactNode {
        const menuDrawer = createRef<MenuDrawer>()
        const occupantsList = createRef<OccupantsList>()
        return <>
            <NavigationBar
                type={NavigationBarType.SEARCH}
                onSearchInputChange={(text: string) => occupantsList.current?.setQuery(text)}
                onMenuClick={() => menuDrawer.current?.setState({ open: true })}
            />
            <MenuDrawerWrapper
                drawerRef={menuDrawer} />
            <div
                style={{
                    marginTop: '60px'
                }}>
                <OccupantsList
                    ref={occupantsList}
                />
            </div>
        </>
    }
}