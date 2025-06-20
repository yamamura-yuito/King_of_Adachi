// frontend/src/pages/SpotList.js
import React, { useEffect, useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { Grid, Card, CardContent, CardMedia, Typography, Button, Container, CircularProgress, Alert, Box } from '@mui/material';

function SpotList() {
  const [spots, setSpots] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    fetch('/api/spots')
      .then((res) => {
        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        setSpots(data || []); // データがnullの場合も考慮
        setLoading(false);
      })
      .catch((err) => {
        console.error("Failed to fetch spots:", err);
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: 'calc(100vh - 64px - 48px)' }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Container sx={{ marginTop: 2 }}>
        <Alert severity="error">スポットの読み込みに失敗しました: {error}</Alert>
      </Container>
    );
  }

  if (!spots || spots.length === 0) {
    return (
      <Container sx={{ marginTop: 2 }}>
        <Alert severity="info">登録されているスポットはありません。</Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 0 }}>
      <Typography variant="h4" component="h1" gutterBottom sx={{ mb: 3 }}>
        スポット一覧
      </Typography>
      <Grid container spacing={3}>
        {spots.map((spot) => (
          <Grid item xs={12} sm={6} md={4} key={spot.id}>
            <Card sx={{ display: 'flex', flexDirection: 'column', height: '100%', '&:hover': { boxShadow: 6, transform: 'scale(1.02)' }, transition: 'box-shadow 0.3s ease-in-out, transform 0.3s ease-in-out' }}>
              {spot.imageUrl && (
                <CardMedia
                  component="img"
                  height="160"
                  image={spot.imageUrl}
                  alt={spot.name}
                />
              )}
              <CardContent sx={{ flexGrow: 1 }}>
                <Typography gutterBottom variant="h5" component="h2" sx={{ fontWeight: 'medium' }}>
                  {spot.name}
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{
                  display: '-webkit-box',
                  WebkitBoxOrient: 'vertical',
                  WebkitLineClamp: 3,
                  overflow: 'hidden',
                  textOverflow: 'ellipsis',
                  minHeight: '60px'
                }}>
                  {spot.description || '説明がありません'}
                </Typography>
              </CardContent>
              <Box sx={{ p: 2, mt: 'auto' }}>
                <Button
                  component={RouterLink}
                  to={`/spots/${spot.id}`}
                  size="medium"
                  variant="contained"
                  fullWidth
                >
                  詳細を見る
                </Button>
              </Box>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
}

export default SpotList;
