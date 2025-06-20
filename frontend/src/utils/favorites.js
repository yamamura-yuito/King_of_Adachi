// お気に入り機能のユーティリティ関数

const FAVORITES_KEY = 'user_favorites';

// ローカルストレージからお気に入りリストを取得
const getFavorites = () => {
  try {
    const favorites = localStorage.getItem(FAVORITES_KEY);
    return favorites ? JSON.parse(favorites) : [];
  } catch (error) {
    console.error('Error reading favorites from localStorage:', error);
    return [];
  }
};

// ローカルストレージにお気に入りリストを保存
const saveFavorites = (favorites) => {
  try {
    localStorage.setItem(FAVORITES_KEY, JSON.stringify(favorites));
  } catch (error) {
    console.error('Error saving favorites to localStorage:', error);
  }
};

// スポットがお気に入りかどうかを確認
export const getFavoriteStatus = (spotId) => {
  const favorites = getFavorites();
  return favorites.includes(spotId);
};

// お気に入りに追加
export const addFavorite = (spotId) => {
  const favorites = getFavorites();
  if (!favorites.includes(spotId)) {
    favorites.push(spotId);
    saveFavorites(favorites);
  }
};

// お気に入りから削除
export const removeFavorite = (spotId) => {
  const favorites = getFavorites();
  const updatedFavorites = favorites.filter(id => id !== spotId);
  saveFavorites(updatedFavorites);
};

// お気に入りリストを取得
export const getFavoriteList = () => {
  return getFavorites();
};

// お気に入りをクリア
export const clearFavorites = () => {
  localStorage.removeItem(FAVORITES_KEY);
}; 