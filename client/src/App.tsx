import { Component } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AnalyticsComponent } from './components/analytics/AnalyticsComponent';

import { MenuDrawerOption } from './components/menu/MenuDrawer';
import { OccupantComponent } from './components/occupant/OccupantComponent';
import { OccupantSearchComponent } from './components/search/OccupantSearchComponent';

interface AppState {
  option: MenuDrawerOption
}

export default class App extends Component<{}, AppState> {
  state: AppState = {
    option: MenuDrawerOption.SEARCH
  }

  render() {
    return (
      <>
        <Router>
          <Routes>
            <Route path='/' element={<OccupantSearchComponent />} />
            <Route path='/occupant/:name' element={<OccupantComponent />} />
            <Route path='/analytics' element={<AnalyticsComponent />} />
          </Routes>
        </Router>
      </>
    )
  }
}
