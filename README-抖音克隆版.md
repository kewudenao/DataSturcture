# 抖音风格网页克隆版

这是一个模仿抖音网站样式的前端网页，可以在本地浏览器中直接打开访问。

## 文件说明

- `douyin-clone.html` - 主HTML文件
- `douyin-style.css` - 样式表文件
- `douyin-script.js` - JavaScript交互功能文件

## 使用方法

### 方法一：直接打开（推荐）
1. 双击 `douyin-clone.html` 文件
2. 文件会在默认浏览器中打开

### 方法二：使用本地服务器
如果需要更好的体验（避免某些浏览器的安全限制），可以使用本地服务器：

**使用 Python（如果已安装）：**
```bash
# Python 3
python -m http.server 8000

# Python 2
python -m SimpleHTTPServer 8000
```

然后在浏览器中访问：`http://localhost:8000/douyin-clone.html`

**使用 Node.js（如果已安装）：**
```bash
# 安装 http-server（如果未安装）
npm install -g http-server

# 启动服务器
http-server
```

然后在浏览器中访问显示的地址。

## 功能特性

### 已实现的功能：
- ✅ 顶部导航栏（固定定位）
- ✅ 左侧功能侧边栏
- ✅ 视频卡片流式布局
- ✅ 视频播放按钮（点击交互）
- ✅ 点赞、评论、分享、转发按钮（带计数）
- ✅ 关注按钮（切换状态）
- ✅ 搜索框（聚焦展开）
- ✅ 响应式设计（适配移动端）
- ✅ 平滑滚动效果
- ✅ 悬停动画效果

### 设计特点：
- 🎨 抖音经典红色主题色（#FF0050）
- 🎨 现代化卡片设计
- 🎨 圆角和阴影效果
- 🎨 流畅的动画过渡
- 🎨 清晰的视觉层次

## 浏览器兼容性

- Chrome/Edge（推荐）
- Firefox
- Safari
- Opera

建议使用最新版本的现代浏览器以获得最佳体验。

## 注意事项

1. 这是一个静态演示页面，不包含实际的视频播放功能
2. 图片使用占位符，可以替换为实际图片
3. 所有交互都是前端演示，不会连接后端服务器
4. 可以根据需要修改样式和内容

## 自定义修改

### 修改主题色
在 `douyin-style.css` 中搜索 `#FF0050` 并替换为您想要的颜色。

### 添加更多视频卡片
在 `douyin-clone.html` 中复制 `.video-card` 结构并修改内容。

### 修改布局
调整 `douyin-style.css` 中的 `.main-container` 和 `.video-feed` 样式。

## 技术栈

- HTML5
- CSS3（Flexbox布局、动画、响应式设计）
- JavaScript（ES6+，DOM操作，事件处理）

