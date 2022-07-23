import { Component, createRef, ReactNode } from 'react';
import { MenuDrawer, MenuDrawerWrapper } from '../menu/MenuDrawer';
import NavigationBar from '../menu/NavigationBar';
import OccupantMap from './OccupantsMap';

export class MapComponent extends Component {
    render(): ReactNode {
        const menuDrawer = createRef<MenuDrawer>()
        return <>
            <NavigationBar
                label='Map'
                onMenuClick={() => menuDrawer.current?.setState({ open: true })}
            />
            <MenuDrawerWrapper
                drawerRef={menuDrawer} />
            <div
                style={{
                    marginTop: '60px'
                }}>
                <OccupantMap />
            </div>
        </>
    }
}