// frontend/src/pages/CheckinList.js
import React, { useEffect, useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { Container, Typography, List, ListItem, ListItemText, ListItemAvatar, Avatar, CircularProgress, Alert, Paper, Divider, Button, Box } from '@mui/material';
import PlaceIcon from '@mui/icons-material/Place';

const MOCK_USER_ID = 1;

function CheckinList() {
  const [checkins, setCheckins] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    fetch(`/api/users/${MOCK_USER_ID}/checkins`)
      .then(res => {
        if (!res.ok) {
          if (res.status === 404) {
            return [];
          }
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        return res.json();
      })
      .then(data => {
        setCheckins(data || []); // データがnullの場合も考慮
        setLoading(false);
      })
      .catch(err => {
        console.error(`Failed to fetch checkins for user ${MOCK_USER_ID}:`, err);
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
        <Alert severity="error">チェックイン履歴の読み込みに失敗しました: {error}</Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 0 }}>
      <Typography variant="h4" component="h1" gutterBottom sx={{ mb: 3 }}>
        チェックイン履歴
      </Typography>
      {checkins.length === 0 ? (
        <Paper elevation={2} sx={{ p:3, textAlign: 'center' }}>
            <Typography variant="subtitle1">チェックイン履歴はまだありません。</Typography>
            <Button component={RouterLink} to="/" variant="outlined" sx={{ mt: 2}}>スポットを探す</Button>
        </Paper>
      ) : (
        <Paper elevation={1} sx={{ mb: 2 }}>
          <List sx={{ bgcolor: 'background.paper' }}>
            {checkins.map((checkin, index) => (
              <React.Fragment key={checkin.id || index}>
                <ListItem
                  alignItems="flex-start"
                  secondaryAction={
                    <Button
                      component={RouterLink}
                      // checkin.spot が存在し、かつ checkin.spot.id が存在する場合のみリンクを有効化
                      to={checkin.spot && checkin.spot.id ? `/spots/${checkin.spot.id}` : '#'}
                      disabled={!(checkin.spot && checkin.spot.id)}
                      size="small"
                      variant="outlined"
                      sx={{ alignSelf: 'center' }}
                    >
                      詳細
                    </Button>
                  }
                  sx={{ '&:hover': { backgroundColor: 'action.hover' }, transition: 'background-color 0.2s' }}
                >
                  <ListItemAvatar sx={{ mt: 0.5 }}>
                    <Avatar
                        // checkin.spot が存在する場合のみ imageUrl と name を参照
                        src={checkin.spot && checkin.spot.imageUrl ? checkin.spot.imageUrl : undefined}
                        alt={checkin.spot && checkin.spot.name ? checkin.spot.name : 'スポット画像'}
                        sx={{ width: 56, height: 56, mr: 1.5, bgcolor: 'primary.light' }}
                    >
                      {/* checkin.spot が存在し、かつ imageUrl がない場合のみ PlaceIcon を表示 */}
                      {checkin.spot && !checkin.spot.imageUrl && <PlaceIcon />}
                      {/* checkin.spot が存在しない場合は何も表示しないか、別のデフォルトアイコン */}
                      {!checkin.spot && <PlaceIcon />}
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary={
                        <Typography variant="h6" component="span" color="text.primary">
                            {/* checkin.spot が存在する場合のみ spot.name を参照 */}
                            {checkin.spot && checkin.spot.name ? checkin.spot.name : 'スポット情報なし'}
                        </Typography>
                    }
                    secondary={
                      <>
                        <Typography
                          sx={{ display: 'block', mt: 0.5 }}
                          component="span"
                          variant="body2"
                          color="text.secondary"
                        >
                          {checkin.checkedInAt ? new Date(checkin.checkedInAt).toLocaleString('ja-JP', { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' }) : '日時不明'}
                        </Typography>
                      </>
                    }
                    sx={{ my: 1 }}
                  />
                </ListItem>
                {index < checkins.length - 1 && <Divider variant="inset" component="li" />}
              </React.Fragment>
            ))}
          </List>
        </Paper>
      )}
    </Container>
  );
}

export default CheckinList;
