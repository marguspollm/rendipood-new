import { Box, Typography } from "@mui/material";

const Footer = () => {
  return (
    <div>
      <Box
        component="footer"
        sx={{
          py: 2,
          px: 2,
          mt: "auto",
          textAlign: "center",
          // bgcolor: "primary.main",
        }}
      >
        <Typography variant="body2" color="text.secondary">
          © {new Date().getFullYear()} All rights reserved.
        </Typography>
      </Box>
    </div>
  );
};

export default Footer;
