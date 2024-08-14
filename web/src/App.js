// src/App.js
import React, {  useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import Login from './components/Certificate/Login';
// import CertificateDetail from './components/CertificateDetail';
import CertificateList from './components/Certificate/CertificateList';
import AddCertificate from './components/Certificate/AddCertificate';
import "./App.css";
import { ListAllUser } from './components/Certificate/ListAllCertificates';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Grid } from '@mui/material';
import AdminPanel from './components/Certificate/AdminPanel';

// Utility function to check token validity
const isTokenValid = (token) => {
  if (!token) return false;

  try {
    const decoded = jwtDecode(token);
    const currentTime = Date.now() / 1000;
    return decoded.exp > currentTime;
  } catch (error) {
    return false;
  }
};

const PrivateRoute = ({ children  , updateNavButtons}) => {
  const token = localStorage.getItem('token');
  if (isTokenValid(token)) {
    return children;
  } else {
    localStorage.removeItem('token');
    updateNavButtons([]);  // Reset the navbar buttons
    return <Navigate to="/login" />;
  }
};
const PrivateRouteAdmin = ({ children , updateNavButtons}) => {
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  if (isTokenValid(token) && role === "ADMIN") {
    return children;
  } else {
    localStorage.removeItem('token');
    updateNavButtons([]);  // Reset the navbar buttons
    return <Navigate to="/login" />;
  }
};

function App() {
  const [navButtons, setNavButtons] = useState([]);
  const updateNavButtons = (buttons) => {
    debugger
    setNavButtons(buttons);
  };
  function AppNavBar({ buttons }) {
    return (
      <AppBar position="sticky" style={{ zIndex: 1 }}>
        <Toolbar  >
          <Grid container spacing={0}>
            <Grid item xs={10}>
              <Typography style={{ fontFamily: "monospace" }} variant="h4" component="div" sx={{ flexGrow: 1 }}>
                OCMS
              </Typography>
            </Grid>
            <Grid item xs={2} >
              <Grid container spacing={0}>
                {buttons && buttons.map((ButtonComponent, index) => (
                  <Grid item xs={6}>
                    <Box key={index} sx={{ ml: 2 }}>
                      {ButtonComponent}
                    </Box>
                  </Grid>
                ))}
              </Grid>
            </Grid>

          </Grid>
        </Toolbar>
      </AppBar>
    );
  }
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppNavBar buttons={navButtons} />
      <Router>
        <Routes>
          <Route updateNavButtons={updateNavButtons} path="/login" element={<Login  updateNavButtons={updateNavButtons} />} />
          <Route
            path="/list"
            element={
              <PrivateRoute updateNavButtons={updateNavButtons}>
                <CertificateList updateNavButtons={updateNavButtons} />
              </PrivateRoute>
            }
          />
          <Route
            path="/view-all"
            element={
              <PrivateRouteAdmin updateNavButtons={updateNavButtons}>
                <AdminPanel updateNavButtons={updateNavButtons} />
              </PrivateRouteAdmin>
            }
          />
          <Route
            path="/"
            element={
              <PrivateRoute updateNavButtons={updateNavButtons}>
                <AddCertificate updateNavButtons={updateNavButtons} />
              </PrivateRoute>
            }
          />
        </Routes>
      </Router>
    </Box>
  );
}

export default App;