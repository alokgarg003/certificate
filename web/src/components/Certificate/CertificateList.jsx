import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Container,
  Typography,
  CircularProgress,
  Button,
} from "@mui/material";
import Grid from "@mui/material/Grid";
import { getAll } from "../service";
import { useNavigate } from "react-router-dom";

const CertificateTable = ({ updateNavButtons }) => {
  const [certificates, setCertificates] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    const buttons = [
      <Button
        onClick={() => {
          navigate("/");
        }}
        variant="contained"
        color="secondary"
      >
        Add
      </Button>,
      <Button
        onClick={() => {
          handleLogout();
        }}
        variant="contained"
        color="secondary"
      >
        Logout
      </Button>,
    ];

    updateNavButtons(buttons);
  }, []);

  useEffect(() => {
    const fetchCertificates = async () => {
      try {
        const response = await getAll();
        setCertificates(response);
      } catch (error) {
        console.error("Error fetching certificates:", error);
      } finally {
        setLoading(false);
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

  return (
    <Container component="main" maxWidth="lg">
      <div style={{ padding: "10px" }}>
        <Grid container spacing={1}>
          <Grid item xs={10}>
            <Typography variant="h4" component="h1" gutterBottom>
              Certificate List
            </Typography>
          </Grid>
          <Grid item xs={1}></Grid>
        </Grid>
      </div>

      {loading ? (
        <CircularProgress />
      ) : (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell>Issuer</TableCell>
                <TableCell>Date</TableCell>
                <TableCell>Certificate Name</TableCell>
                <TableCell>PDF</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {certificates.map((certificate) => (
                <TableRow key={certificate.id}>
                  <TableCell>{certificate.name}</TableCell>
                  <TableCell>{certificate.issuer}</TableCell>
                  <TableCell>{certificate.date}</TableCell>
                  <TableCell>{certificate.certificateName}</TableCell>
                  <TableCell>
                    {certificate.authorized && (
                      <Button
                        variant="contained"
                        onClick={() =>
                          handleDownload(
                            certificate.pdfContent,
                            `${certificate.name}.pdf`
                          )
                        }
                      >
                        Download
                      </Button>
                    )}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Container>
  );
};

export default CertificateTable;
