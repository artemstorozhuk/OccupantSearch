import { Component, createRef, ReactNode } from 'react';
import { MenuDrawer, MenuDrawerWrapper } from '../menu/MenuDrawer';
import NavigationBar, { NavigationBarType } from '../menu/NavigationBar';
import GroupList from './GroupList';

export class GroupComponent extends Component {
    render(): ReactNode {
        const menuDrawer = createRef<MenuDrawer>()
        return <>
            <NavigationBar
                type={NavigationBarType.EMPTY}
                onSearchInputChange={() => { }}
                onMenuClick={() => menuDrawer.current?.setState({ open: true })}
            />
            <MenuDrawerWrapper
                drawerRef={menuDrawer} />
            <div
                style={{
                    marginTop: '60px'
                }}>
                <GroupList />
            </div>
        </>
    }
}