import React, { useEffect, useState } from "react";
import { TextField, Button, Box, Container, Typography } from "@mui/material";
import { addCertificate } from "../service";
import { certificateObj } from "../Util";
import { useNavigate } from "react-router-dom";

const CertificateForm = ({updateNavButtons}) => {
  const [formData, setFormData] = useState(certificateObj);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const buttons = [
      <Button
        onClick={() => {
          navigate("/list");
        }}
        color="secondary"
        variant="contained"
      >
        Certificates
      </Button>,
    ];
    updateNavButtons(buttons)
  }, []);

  useEffect(() => {
    try {
      if (localStorage.getItem("role") === "ADMIN") {
        navigate("/view-all");
      }
    } catch (error) {
      console.error("Error navigating:", error);
    }
  }, [navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validate = () => {
    let tempErrors = {};
    const whitespacePattern = /^\s*$/;

    if (!formData.name || whitespacePattern.test(formData.name)) {
      tempErrors.name = "Name is required and cannot be blank.";
    } else if (formData.name.length < 3) {
      tempErrors.name = "Name must be at least 3 characters long.";
    }

    if (!formData.issuer || whitespacePattern.test(formData.issuer)) {
      tempErrors.issuer = "Issuer is required and cannot be blank.";
    } else if (formData.issuer.length < 3) {
      tempErrors.issuer = "Issuer must be at least 3 characters long.";
    }

    if (
      !formData.certificateName ||
      whitespacePattern.test(formData.certificateName)
    ) {
      tempErrors.certificateName =
        "Certificate Name is required and cannot be blank.";
    } else if (formData.certificateName.length < 3) {
      tempErrors.certificateName =
        "Certificate Name must be at least 3 characters long.";
    }

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validate()) {
      try {
        await addCertificate(formData);
        setFormData(certificateObj);
        navigate("/list");
      } catch (error) {
        console.error("Error adding certificate:", error);
      }
    }
  };

  return (
    <Container component="main" maxWidth="sm">
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography component="h1" variant="h5">
          Certificate Form
        </Typography>
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="name"
            label="Name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            error={!!errors.name}
            helperText={errors.name}
            autoFocus
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="issuer"
            label="Issuer"
            name="issuer"
            value={formData.issuer}
            onChange={handleChange}
            error={!!errors.issuer}
            helperText={errors.issuer}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="certificateName"
            label="Certificate Name"
            name="certificateName"
            value={formData.certificateName}
            onChange={handleChange}
            error={!!errors.certificateName}
            helperText={errors.certificateName}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Submit
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default CertificateForm;
