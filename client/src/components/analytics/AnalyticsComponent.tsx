import { Component, createRef, ReactNode } from 'react';
import { MenuDrawer, MenuDrawerWrapper } from '../menu/MenuDrawer';
import NavigationBar, { NavigationBarType } from '../menu/NavigationBar';
import AnalyticsChart from './AnalyticsChart';

export class AnalyticsComponent extends Component {
    render(): ReactNode {
        const menuDrawer = createRef<MenuDrawer>()
        return <>
            <NavigationBar
                type={NavigationBarType.LABEL}
                label='Analytics'
                onMenuClick={() => menuDrawer.current?.setState({ open: true })}
            />
            <MenuDrawerWrapper
                drawerRef={menuDrawer} />
            <div
                style={{
                    marginTop: '60px'
                }}>
                <AnalyticsChart />
            </div>
        </>
    }
}