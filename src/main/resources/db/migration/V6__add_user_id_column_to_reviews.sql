-- 1. user_idカラムを追加
ALTER TABLE reviews ADD COLUMN user_id INTEGER;

-- 2. 既存のレコードにデフォルトのuser_idを設定
UPDATE reviews SET user_id = 1 WHERE user_id IS NULL;

-- 3. 外部キー制約を追加
ALTER TABLE reviews
ADD CONSTRAINT fk_reviews_user
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- NOT NULL制約を追加
ALTER TABLE reviews ALTER COLUMN user_id SET NOT NULL;