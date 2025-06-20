// frontend/src/App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Box, CssBaseline, Toolbar, ThemeProvider } from '@mui/material';
import Header from './components/Header';
import ResponsiveDrawer from './components/ResponsiveDrawer';
import SpotList from './pages/SpotList';
import SpotDetail from './pages/SpotDetail';
import CheckinList from './pages/CheckinList';
import FavoriteList from './pages/FavoriteList'; // FavoriteList をインポート
import theme from './theme';

const drawerWidth = 240;

function App() {
  const [mobileOpen, setMobileOpen] = useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Box sx={{ display: 'flex' }}>
          <CssBaseline />
          <Header drawerWidth={drawerWidth} handleDrawerToggle={handleDrawerToggle} />
          <ResponsiveDrawer
            drawerWidth={drawerWidth}
            mobileOpen={mobileOpen}
            handleDrawerToggle={handleDrawerToggle}
          />
          <Box
            component="main"
            sx={{
              flexGrow: 1,
              p: 3,
              width: { sm: `calc(100% - ${drawerWidth}px)` },
              backgroundColor: (theme) => theme.palette.background.default,
              minHeight: '100vh',
            }}
          >
            <Toolbar />
            <Routes>
              <Route path="/" element={<SpotList />} />
              <Route path="/spots/:id" element={<SpotDetail />} />
              <Route path="/checkins" element={<CheckinList />} />
              <Route path="/favorites" element={<FavoriteList />} /> {/* お気に入り一覧へのルートを追加 */}
            </Routes>
          </Box>
        </Box>
      </Router>
    </ThemeProvider>
  );
}

export default App;