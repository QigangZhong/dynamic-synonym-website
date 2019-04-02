# dynamic-synonym-website
动态同义词库示例站点

```
DROP TABLE IF EXISTS `synonym_config`;
CREATE TABLE `synonym_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `synonyms` varchar(128) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `synonym_config` VALUES ('1', '西红柿,番茄,圣女果', '2019-04-01 16:10:16');
INSERT INTO `synonym_config` VALUES ('2', '馄饨,抄手', '2019-04-02 16:10:40');
INSERT INTO `synonym_config` VALUES ('5', '小说,笑说,晓说', '2019-03-31 17:23:36');
INSERT INTO `synonym_config` VALUES ('6', '你好,利好', '2019-04-02 17:27:06');
```
