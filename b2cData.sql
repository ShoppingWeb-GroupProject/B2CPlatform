-- --------------------------------------------------------
-- 主機:                           127.0.0.1
-- 伺服器版本:                        10.6.4-MariaDB - mariadb.org binary distribution
-- 伺服器作業系統:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 ism1tkefynz1jgyp 的資料庫結構
CREATE DATABASE IF NOT EXISTS `ism1tkefynz1jgyp` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci */;
USE `b2cshop`;

-- 傾印  資料表 b2cshop.cart_items 結構
CREATE TABLE IF NOT EXISTS `cart_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT 1,
  `added_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.cart_items 的資料：~0 rows (近似值)
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;

-- 傾印  資料表 b2cshop.categories 結構
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `state` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.categories 的資料：~5 rows (近似值)
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
REPLACE INTO `categories` (`id`, `name`, `description`, `state`) VALUES
	(1, '帆布鞋', '輕便透氣、適合日常穿搭的帆布材質鞋款', 1),
	(2, '慢跑鞋', '具避震功能、支撐性佳，適合運動與長時間行走', 1),
	(3, '球鞋', '強化抓地力與腳踝支撐，適合籃球、足球等運動使用', 1),
	(4, '涼鞋/拖鞋', '適合夏季或室內穿著，透氣舒適、穿脫方便', 1),
	(5, '靴子', '保暖、防水，適合秋冬季節與戶外活動穿搭', 1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;

-- 傾印  資料表 b2cshop.orders 結構
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `total_amount` double NOT NULL,
  `address` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `status` enum('pending','paid','shipped','completed','cancelled') COLLATE utf8mb3_unicode_ci DEFAULT 'pending',
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.orders 的資料：~30 rows (近似值)
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
REPLACE INTO `orders` (`id`, `user_id`, `total_amount`, `address`, `status`, `created_at`) VALUES
	(1, 2, 2000, '神秘東方古國', 'cancelled', '2025-05-10 09:44:03'),
	(2, 2, 10000, '神秘東方古國', 'cancelled', '2025-05-10 10:30:35'),
	(3, 2, 4000, '神秘東方古國', 'pending', '2025-05-10 15:19:39'),
	(4, 2, 20000, '神秘東方古國', 'pending', '2025-05-11 03:13:45'),
	(5, 2, 4000, '神秘東方古國', 'shipped', '2025-05-11 06:06:58'),
	(6, 2, 20000, '神秘東方古國', 'pending', '2025-05-11 08:49:38'),
	(7, 2, 2000, '神秘東方古國', 'pending', '2025-05-11 08:56:22'),
	(8, 2, 8000, '神秘東方古國', 'pending', '2025-05-12 13:07:31'),
	(9, 2, 2000, '神秘東方古國', 'pending', '2025-05-12 14:16:18'),
	(10, 4, 12000, '神秘東方古國', 'shipped', '2025-05-13 13:33:44'),
	(11, 2, 20000, '神秘東方古國', 'completed', '2025-05-13 13:36:22'),
	(12, 3, 2000, '神秘東方古國', 'shipped', '2025-05-13 13:45:43'),
	(13, 3, 2000, '神秘東方古國', 'pending', '2025-05-13 13:54:15'),
	(14, 3, 2000, '神秘東方古國', 'pending', '2025-05-13 13:57:54'),
	(15, 3, 4000, '神秘東方古國', 'cancelled', '2025-05-13 14:10:33'),
	(16, 2, 8880, '神秘東方古國', 'cancelled', '2025-05-15 15:52:55'),
	(17, 2, 17776, '神秘東方古國', 'shipped', '2025-05-16 12:29:09'),
	(18, 2, 2850, '神秘東方古國', 'pending', '2025-05-17 07:25:35'),
	(19, 2, 888, '神秘東方古國', 'cancelled', '2025-05-18 02:35:04'),
	(20, 2, 2850, '神秘東方古國', 'shipped', '2025-05-18 03:13:48'),
	(21, 2, 2850, '神秘東方古國', 'shipped', '2025-05-18 04:35:17'),
	(22, 2, 888, '神秘東方古國', 'shipped', '2025-05-18 04:41:53'),
	(23, 2, 26664, '神秘東方古國', 'pending', '2025-05-20 13:43:19'),
	(24, 2, 3998, '神秘東方古國', 'pending', '2025-05-20 14:05:24'),
	(25, 2, 2400, '神秘東方古國', 'cancelled', '2025-05-20 14:28:48'),
	(26, 2, 19998, '神秘東方古國', 'pending', '2025-05-22 12:27:31'),
	(27, 2, 35552, '神秘東方古國', 'pending', '2025-05-22 12:36:50'),
	(28, 2, 8888, '西方極樂世界', 'pending', '2025-05-22 12:46:24'),
	(29, 2, 26664, '西方極樂世界', 'cancelled', '2025-05-22 13:14:21'),
	(30, 2, 8888, '西方極樂世界', 'pending', '2025-05-24 14:21:38');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- 傾印  資料表 b2cshop.order_items 結構
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.order_items 的資料：~32 rows (近似值)
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
REPLACE INTO `order_items` (`id`, `order_id`, `product_id`, `quantity`, `price`) VALUES
	(1, 1, 1, 1, 2000),
	(2, 2, 1, 1, 2000),
	(3, 2, 2, 2, 4000),
	(4, 3, 1, 2, 2000),
	(5, 4, 2, 5, 4000),
	(6, 5, 2, 1, 4000),
	(7, 6, 1, 10, 2000),
	(8, 7, 1, 1, 2000),
	(9, 8, 1, 4, 2000),
	(10, 9, 1, 1, 2000),
	(11, 10, 2, 3, 4000),
	(12, 11, 2, 5, 4000),
	(13, 12, 1, 1, 2000),
	(14, 13, 1, 1, 2000),
	(15, 14, 1, 1, 2000),
	(16, 15, 1, 2, 2000),
	(17, 16, 3, 10, 888),
	(18, 17, 2, 2, 8888),
	(19, 18, 6, 1, 2850),
	(20, 19, 3, 1, 888),
	(21, 20, 6, 1, 2850),
	(22, 21, 6, 1, 2850),
	(23, 22, 3, 1, 888),
	(24, 23, 2, 3, 8888),
	(25, 24, 9, 2, 1999),
	(26, 25, 10, 2, 1200),
	(27, 26, 4, 3, 6666),
	(28, 27, 7, 3, 8888),
	(29, 27, 2, 1, 8888),
	(30, 28, 2, 1, 8888),
	(31, 29, 2, 3, 8888),
	(32, 30, 2, 1, 8888);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;

-- 傾印  資料表 b2cshop.products 結構
CREATE TABLE IF NOT EXISTS `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `price` double NOT NULL,
  `stock` int(11) DEFAULT 0,
  `image_url` text COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.products 的資料：~10 rows (近似值)
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
REPLACE INTO `products` (`id`, `seller_id`, `name`, `description`, `category_id`, `price`, `stock`, `image_url`, `created_at`) VALUES
	(2, 4, 'Kixado球鞋', '這雙Kixado高筒潮鞋採用優質皮革製成，具備極佳的透氣性與耐久性。紅色經典側邊設計結合黑白配色，展現強烈街頭風格。鞋領厚實包覆，提升腳踝支撐與舒適度；鞋底抓地力佳，適合長時間行走。無論日常穿搭或運動休閒皆能展現獨特品味與機能性。', 3, 8888, 500, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747323260/ajsfoxyjjl6awccjbxvd.png', '2025-05-15 15:34:23'),
	(3, 4, 'K白拖', '這雙Kixado涼鞋採用一體成型設計，輕量且支撐力強，提供絕佳穿著舒適感。防滑鞋底搭配凹凸紋理，有效提升抓地力，適合居家或戶外穿搭。簡約米色搭配黑色K字Logo，展現潮流風格與實用機能兼具的設計理念。', 4, 888, 0, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747324306/nleveogkijnmhbabjuxt.png', '2025-05-15 15:51:48'),
	(4, 4, 'Kixado工裝靴', '這雙Kixado工裝靴採用耐磨防潑水皮革，搭配厚實橡膠大底，提供強力抓地與穩定支撐。高筒設計包覆性佳，搭配加厚鞋口減少磨腳，適合戶外工作與冬季穿搭。經典焦糖色與K字Logo展現硬派風格與品牌識別。\r\n', 5, 6666, 100, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747390235/ahpxkz38echoery3ch6l.png', '2025-05-16 10:10:38'),
	(5, 4, 'Kixado慢跑鞋', '這雙Kixado慢跑鞋採用透氣網布與輕量結構設計，提升運動時的舒適與靈活性。中底搭載高彈EVA泡棉，有效吸震減壓，搭配防滑耐磨外底，提供穩定抓地力。簡約灰色系搭配側邊K字Logo，展現俐落運動風格，適合日常跑步與健走穿著。\r\n', 2, 1999, 500, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747390295/d3agapd5839fiueiwqui.png', '2025-05-16 10:11:38'),
	(6, 4, 'Kixado帆布鞋', '這雙Kixado帆布鞋採用經典黑白配色，搭配對比車線與橡膠鞋頭，外型簡約百搭。鞋面選用高密度帆布材質，耐磨透氣，適合長時間穿著。鞋底採用防滑紋理設計，行走更穩定，無論日常出行或休閒穿搭皆適用。\r\n', 1, 2850, 599, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747411929/pmmilis69owyq0twoa5o.png', '2025-05-16 16:12:12'),
	(7, 4, 'K球鞋', '這雙Kixado高筒球鞋結合黑紅撞色設計與動感線條，展現強烈運動風格。鞋面採用透氣網布與合成皮革，兼顧支撐與舒適性。厚實中底搭配避震結構，有效吸收落地衝擊；大底採用人字紋設計，提供優異抓地力，適合籃球等高強度運動。\r\n\r\n', 3, 8888, 200, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747747603/cpofqt4veb7uekrv1yml.png', '2025-05-20 13:26:45'),
	(8, 4, 'Kixado涼鞋', '這雙Kixado涼鞋採用一體成型設計，外型極簡，搭配米色鞋身與大K字Logo，展現品牌識別。鞋面柔軟包覆，穿脫方便，鞋床依腳型曲線設計，提供舒適支撐。防滑波紋大底加強抓地力，適合日常外出、居家與休閒穿搭使用。\r\n\r\n', 4, 999, 50, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747747761/mlttny9udjcdrjzikjs6.png', '2025-05-20 13:29:23'),
	(9, 4, 'K帆布鞋', '這雙Kixado帆布鞋以黑色帆布搭配白色厚底，展現簡約復古風格。鞋身以耐磨布料製成，透氣舒適，適合長時間穿著；金屬鞋孔與扁平鞋帶增添細節質感。橡膠大底止滑耐用，無論日常通勤或休閒出遊皆宜。K字Logo與品牌名側印強化識別度。\r\n', 1, 1999, 5, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747749778/cwdce7mbdsczospro3mt.png', '2025-05-20 13:35:55'),
	(10, 4, 'K慢跑鞋', '這雙Kixado慢跑鞋以淺灰配色搭配網眼布鞋面，兼具透氣性與輕量結構設計。中底採用柔軟高彈泡棉，有效吸收衝擊、減少膝蓋負擔。鞋底防滑紋路設計提升抓地力，無論健走或慢跑皆穩定安全。側邊K字Logo與品牌名展現簡潔運動風格。\r\n\r\n', 2, 1200, 999, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747750196/qz0uzglh1muyk3vwmcrm.png', '2025-05-20 14:09:59'),
	(11, 4, 'K靴子', '這雙Kixado靴子採用防潑水絨面皮革，搭配加厚內裡與高筒設計，提供保暖與腳踝支撐。鞋底採用深紋路防滑橡膠，增強抓地力與穩定性，適合戶外工作與冬季穿搭。金屬鞋扣與白色K字Logo突顯工裝風格與品牌識別。\r\n', 5, 6999, 3, 'https://res.cloudinary.com/dsnzdecej/image/upload/v1747750466/fv7i0rfeljegjprfjnsy.png', '2025-05-20 14:14:29');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- 傾印  資料表 b2cshop.replies 結構
CREATE TABLE IF NOT EXISTS `replies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `review_id` int(11) NOT NULL,
  `seller_id` int(11) NOT NULL,
  `reply` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  KEY `replies_ibfk_1` (`review_id`),
  CONSTRAINT `replies_ibfk_1` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`) ON DELETE CASCADE,
  CONSTRAINT `replies_ibfk_2` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.replies 的資料：~2 rows (近似值)
/*!40000 ALTER TABLE `replies` DISABLE KEYS */;
REPLACE INTO `replies` (`id`, `review_id`, `seller_id`, `reply`, `created_at`) VALUES
	(7, 11, 4, '哈哈', '2025-05-17 13:21:54'),
	(10, 14, 4, '3Q', '2025-05-17 16:49:52');
/*!40000 ALTER TABLE `replies` ENABLE KEYS */;

-- 傾印  資料表 b2cshop.reviews 結構
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `rating` int(11) DEFAULT NULL CHECK (`rating` between 1 and 5),
  `comment` text COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `reply` text COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.reviews 的資料：~3 rows (近似值)
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
REPLACE INTO `reviews` (`id`, `product_id`, `user_id`, `rating`, `comment`, `created_at`, `reply`) VALUES
	(11, 2, 3, 5, '鞋子好穿 大推', '2025-05-17 12:36:39', NULL),
	(14, 6, 3, 5, 'cp很高', '2025-05-17 16:48:07', NULL),
	(15, 4, 2, 3, 'OK啦', '2025-05-22 12:25:35', NULL);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;

-- 傾印  資料表 b2cshop.users 結構
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `role` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT 'buyer',
  `is_blacklisted` tinyint(1) DEFAULT 0,
  `discount` double DEFAULT 1,
  `line_id` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- 正在傾印表格  b2cshop.users 的資料：~4 rows (近似值)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`id`, `username`, `password`, `email`, `phone`, `address`, `role`, `is_blacklisted`, `discount`, `line_id`) VALUES
	(2, 'cody', '000', 'cody.7658@gmail.com', '0986609912', '神秘東方古國', 'buyer', 0, 0.8, 'Uff5b526e196b71862417aad30424a859'),
	(3, 'charlie', '123', 'charlielin266@gmail.com', '0987654321', '123', 'buyer', 0, 1, 'U94185ff6e167e609ea062731031a1256'),
	(4, 'seller', '000', 'seller@gmail.com', '0911223344', 'seller', 'seller', 0, 1, NULL),
	(5, 'celine', '123456', 'celine4231@gmail.com', '0963815147', '快樂之地', 'buyer', 0, 1, 'U7cc635a06a093e6d2508a5bddf072944');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
