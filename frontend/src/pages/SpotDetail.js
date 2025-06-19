// frontend/src/pages/SpotDetail.js
import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Typography, CardMedia, Button, CircularProgress, Alert, Box, Paper, IconButton } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import { getFavoriteStatus, addFavorite, removeFavorite } from '../utils/favorites';

const MOCK_USER_ID = 1;

function SpotDetail() {
  const { id } = useParams();
  const spotId = Number(id);
  const [spot, setSpot] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [checkinLoading, setCheckinLoading] = useState(false);
  const [checkinError, setCheckinError] = useState(null);
  const [checkinSuccess, setCheckinSuccess] = useState(false);
  const [isFavorite, setIsFavorite] = useState(false);

  useEffect(() => {
    if (spot && !isNaN(spotId)) {
      setIsFavorite(getFavoriteStatus(spotId));
    }
  }, [spot, spotId]);

  const handleFavoriteToggle = useCallback(() => {
    if (isNaN(spotId)) return;

    if (isFavorite) {
      removeFavorite(spotId);
      setIsFavorite(false);
    } else {
      addFavorite(spotId);
      setIsFavorite(true);
    }
  }, [isFavorite, spotId]);

  useEffect(() => {
    if (isNaN(spotId)) {
      setError("無効なスポットIDです。");
      setLoading(false);
      return;
    }
    setLoading(true);
    fetch(`/api/spots/${spotId}`)
      .then(res => {
        if (!res.ok) {
          if (res.status === 404) {
            throw new Error('Spot not found');
          }
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        return res.json();
      })
      .then(data => {
        setSpot(data || null); // データがnullの場合も考慮
        setLoading(false);
      })
      .catch(err => {
        console.error(`Failed to fetch spot with id ${spotId}:`, err);
        setError(err.message);
        setLoading(false);
      });
  }, [spotId]);

  const handleCheckin = () => {
    if (isNaN(spotId) || !spot) return;

    setCheckinLoading(true);
    setCheckinError(null);
    setCheckinSuccess(false);
    fetch(`/api/users/${MOCK_USER_ID}/checkins`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ spotId: spot.id }),
    })
    .then(res => {
      if (!res.ok) {
        return res.json().then(errData => {
          throw new Error(errData.error || `Check-in failed with status: ${res.status}`);
        });
      }
      return res.json();
    })
    .then(data => {
      setCheckinSuccess(true);
      setCheckinLoading(false);
    })
    .catch(err => {
      console.error("Check-in failed:", err);
      setCheckinError(err.message);
      setCheckinLoading(false);
    });
  };

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
        <Alert severity="error">{error}</Alert>
      </Container>
    );
  }

  if (!spot) {
    return (
      <Container sx={{ marginTop: 2 }}>
        <Alert severity="info">スポット情報が見つかりません。</Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 0 }}>
      <Paper elevation={3} sx={{ padding: { xs: 2, sm: 3 }, mt: 2, position: 'relative' }}>
        <IconButton
          aria-label="toggle favorite"
          onClick={handleFavoriteToggle}
          disabled={isNaN(spotId)}
          sx={{
            position: 'absolute',
            top: { xs: 8, sm: 16 },
            right: { xs: 8, sm: 16 },
            zIndex: 1,
            color: isFavorite ? 'error.main' : 'action.active',
            backgroundColor: 'rgba(255, 255, 255, 0.8)',
            '&:hover': {
              backgroundColor: 'rgba(255, 255, 255, 1)',
            },
            transform: 'scale(0.9)',
            '@media (min-width:600px)': {
              transform: 'scale(1)',
            }
          }}
        >
          {isFavorite ? <FavoriteIcon fontSize="large" /> : <FavoriteBorderIcon fontSize="large" />}
        </IconButton>

        <Typography variant="h3" component="h1" gutterBottom sx={{ fontWeight: 'bold', mb: 2, pr: {xs: '40px', sm: '48px'}  }}>
          {spot.name}
        </Typography>

        {spot.imageUrl && (
          <CardMedia
            component="img"
            height="350"
            image={spot.imageUrl}
            alt={spot.name}
            sx={{ marginBottom: 3, borderRadius: 1 }}
          />
        )}

        <Typography variant="h5" component="h2" gutterBottom sx={{ mt: 3, mb: 1, borderBottom: '2px solid', borderColor: 'primary.main', pb: 0.5 }}>
          詳細情報
        </Typography>
        <Typography variant="body1" paragraph sx={{ lineHeight: 1.8, whiteSpace: 'pre-wrap' }}>
          {spot.description || '詳細な説明はありません。'}
        </Typography>

        {spot.latitude && spot.longitude && (
          <Box sx={{ marginTop: 3, marginBottom: 3 }}>
            <Typography variant="h6" gutterBottom sx={{ borderBottom: '2px solid', borderColor: 'primary.light', pb: 0.5, mb: 1 }}>位置情報</Typography>
            <Typography variant="body2">緯度: {spot.latitude}, 経度: {spot.longitude}</Typography>
          </Box>
        )}

        <Box sx={{ marginTop: 4, textAlign: 'center' }}>
          <Button
            variant="contained"
            color="primary"
            onClick={handleCheckin}
            disabled={checkinLoading || checkinSuccess || isNaN(spotId) || !spot}
            size="large"
            sx={{ minWidth: '200px' }}
          >
            {checkinLoading ? <CircularProgress size={24} color="inherit" /> : (checkinSuccess ? 'チェックイン済み' : 'この場所にチェックイン')}
          </Button>
          {checkinError && <Alert severity="error" sx={{ marginTop: 2 }}>{checkinError}</Alert>}
          {checkinSuccess && <Alert severity="success" sx={{ marginTop: 2 }}>チェックインしました！</Alert>}
        </Box>
      </Paper>
    </Container>
  );
}

export default SpotDetail;
