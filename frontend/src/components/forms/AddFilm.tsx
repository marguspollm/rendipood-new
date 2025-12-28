import { type ChangeEventHandler } from "react";
import type { FilmType } from "../../models/FilmType";
import { Box, TextField, MenuItem, Button } from "@mui/material";
import type { Film } from "../../models/Film";

function AddFilm({
  form,
  onSubmit,
  onChange,
}: {
  form: Film;
  onSubmit: () => void;
  onChange: ChangeEventHandler;
}) {
  const filmTypes: FilmType[] = ["NEW", "REGULAR", "OLD"];

  return (
    <>
      <Box
        component="form"
        sx={{ display: "flex", flexDirection: "row", gap: 2 }}
      >
        <TextField
          label="Title"
          name="title"
          value={form.title}
          onChange={(e) => onChange(e)}
          required
        />
        <TextField
          select
          label="Type"
          name="type"
          value={form.type}
          onChange={(e) => onChange(e)}
          required
        >
          {filmTypes.map((t) => (
            <MenuItem key={t} value={t}>
              {t}
            </MenuItem>
          ))}
        </TextField>
        <Button variant="contained" onClick={() => onSubmit}>
          Submit
        </Button>
      </Box>
    </>
  );
}

export default AddFilm;
