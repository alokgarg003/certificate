import React, { useEffect, useState } from "react";
import { authorizeCertificatebyId, deauthorizeCertificatebyId, getAllCertificates } from "../service";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  Typography,
} from "@mui/material";
import Grid from "@mui/material/Grid";
import { useNavigate } from "react-router-dom";
import { CreateUserModal } from "./CreateUserModal";

const ListAllCertificates = ({ updateNavButtons }) => {
  const [certificates, setCertificates] = useState([]);

  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const navigate = useNavigate();

  useEffect(() => {
    const buttons = [
      <Button onClick={handleOpen} color="secondary" variant="contained">
        Create User
      </Button>,
      <Button
        onClick={() => {
          handleLogout();
        }} 
        color="secondary" 
        variant="contained"
      >
        Logout
      </Button>,
    ];
    updateNavButtons(buttons);
  }, []);

  useEffect(() => {
    const fetchCertificates = async () => {
      try {
        const response = await getAllCertificates();
        setCertificates(response);
      } catch (error) {
        console.error("Error fetching certificates:", error);
      } finally {
      }
    };

    fetchCertificates();
  }, []);

  const handleDownload = (base64String, fileName) => {
    const byteCharacters = atob(base64String);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);

    const blob = new Blob([byteArray], { type: "application/pdf" });

    const link = document.createElement("a");

    const url = URL.createObjectURL(blob);

    link.href = url;
    link.download = fileName;

    document.body.appendChild(link);

    link.click();

    document.body.removeChild(link);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const authorizeCertificate = async (id) => {
    try {
      const response = await authorizeCertificatebyId(id);
      response === "Ok" && navigate(0);
    } catch (error) {
      console.error("Error Auhtorizing certificate:", error);
    }
  };

  const deauthorizeCertificate = async (id) => {
    try {
      const response = await deauthorizeCertificatebyId(id);
      response === "Ok" && navigate(0);
    } catch (error) {
      console.error("Error Auhtorizing certificate:", error);
    }
  };

  return (
    <div style={{ padding: "10px" }}>
      <div style={{ padding: "10px" }}>
        <Grid container spacing={0}>
          <Grid item xs={10}>
            <Typography variant="h4" component="h1" gutterBottom>
              Certificate List
            </Typography>
          </Grid>
          <Grid item xs={1}></Grid>
          <CreateUserModal open={open} handleClose={handleClose} />
          <Grid item xs={1}></Grid>
        </Grid>
      </div>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              {/* <TableCell>ID</TableCell> */}
              <TableCell>Name</TableCell>
              <TableCell>Issuer</TableCell>
              <TableCell>Date</TableCell>
              <TableCell>Certificate Name</TableCell>
              <TableCell>User ID</TableCell>
              <TableCell>PDF Content</TableCell>
              <TableCell>Authorize</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {certificates.map((obj, i) => (
              <TableRow key={i}>
                {/* <TableCell>{obj.id}</TableCell> */}
                <TableCell>{obj.name}</TableCell>
                <TableCell>{obj.issuer}</TableCell>
                <TableCell>{obj.date}</TableCell>
                <TableCell>{obj.certificateName}</TableCell>
                <TableCell>{obj.userId}</TableCell>
                <TableCell>
                  {obj.authorized && (
                    <Button
                      variant="contained"
                      onClick={() =>
                        handleDownload(obj.pdfContent, `${obj.name}.pdf`)
                      }
                    >
                      Download
                    </Button>
                  )}
                </TableCell>
                <TableCell>
                  {!obj.authorized ? (
                    <Button
                      onClick={() => {
                        authorizeCertificate(obj.id);
                      }}
                      variant="contained"
                      color="info"
                    >
                      Authorize
                    </Button>
                  ) : (
                    <Button  onClick={() => {
                      deauthorizeCertificate(obj.id);
                    }} variant="contained" color="success">
                      Authorized
                    </Button>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};
export { ListAllCertificates };
