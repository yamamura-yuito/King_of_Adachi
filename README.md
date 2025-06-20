# 足立区全区域制覇アプリ (King of Adachi)

これは「足立区全区域制覇アプリ (King of Adachi)」のバックエンドリポジトリです。
このアプリケーションは、ユーザーが足立区内の様々なスポットを訪れ、スタンプを集めることを目的としたSpring Bootウェブアプリケーションです。

## 主な機能（要件）

- 足立区のスポットを訪れるとスタンプを獲得できます。
- 今まで訪れたスタンプの一覧を閲覧できます。
- 各スポットの詳細情報（説明、場所など）を見ることができます。

## 技術スタック（非機能要件）

- **プログラミング言語:** Java
- **フレームワーク:** Spring Boot (Spring MVC)
- **アーキテクチャ:** ドメイン駆動設計 (DDD) + オニオンアーキテクチャ
- **ORM:** MyBatis
- **データベース:** MySQL
- **ビルドツール:** Maven
- **フロントエンド:** レスポンシブデザイン（具体的な技術はフロントエンドリポジトリに依存）

## APIエンドポイント

現在実装されている主なAPIエンドポイントは以下の通りです（ベースパス: `/api/spots`）。

- `POST /api/spots`: 新しいスポットを作成します。
  - リクエストボディ: スポット情報 (JSON形式)
  - レスポンス: 作成されたスポット情報
- `GET /api/spots/{id}`: 指定されたIDのスポット情報を取得します。
  - パスパラメータ: `id` (スポットID)
  - レスポンス: スポット情報
- `GET /api/spots`: すべてのスポット情報を取得します。
  - レスポンス: スポット情報のリスト

- `POST /api/users/{userId}/checkins`: ユーザーが特定のスポットで新しいチェックイン（スタンプ）を作成します。
  - パスパラメータ: `userId` (ユーザーID)
  - リクエストボディ: `spotId` を含むJSONオブジェクト (例: `{"spotId": 123}`)
  - レスポンス: 作成されたチェックイン情報
- `GET /api/users/{userId}/checkins`: 特定のユーザーのすべてのチェックイン（スタンプ）履歴を取得します。
  - パスパラメータ: `userId` (ユーザーID)
  - レスポンス: チェックイン情報のリスト

## セットアップと実行

(ここにローカル環境でのセットアップ方法や実行方法を記載 - 将来的に追記)

## コントリビュート

(ここにコントリビュート方法を記載 - 将来的に追記)
