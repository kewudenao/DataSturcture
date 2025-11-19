// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    initSearch();
    initSidebar();
    initVideoPlayer();
    initActionButtons();
    initFollowButton();
    initVideoControls();
});

// 初始化搜索功能
function initSearch() {
    const searchInput = document.getElementById('searchInput');
    const searchContainer = document.querySelector('.search-container');
    const searchSuggestions = document.getElementById('searchSuggestions');
    
    if (searchInput) {
        searchInput.addEventListener('focus', function() {
            searchContainer.classList.add('active');
        });
        
        searchInput.addEventListener('blur', function() {
            // 延迟隐藏，以便点击建议项
            setTimeout(() => {
                searchContainer.classList.remove('active');
            }, 200);
        });
        
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                const query = this.value.trim();
                if (query) {
                    alert(`搜索: ${query}`);
                }
            }
        });
    }
    
    // 搜索建议点击事件
    if (searchSuggestions) {
        const suggestionItems = searchSuggestions.querySelectorAll('.suggestion-item');
        suggestionItems.forEach(item => {
            item.addEventListener('click', function() {
                searchInput.value = this.textContent;
                searchContainer.classList.remove('active');
                alert(`搜索: ${this.textContent}`);
            });
        });
    }
}

// 初始化侧边栏
function initSidebar() {
    const sidebarItems = document.querySelectorAll('.sidebar-item');
    
    sidebarItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            
            // 移除所有活动状态
            sidebarItems.forEach(si => {
                si.classList.remove('active');
            });
            
            // 添加当前活动状态
            this.classList.add('active');
            
            // 显示提示
            const itemName = this.querySelector('span').textContent;
            console.log(`切换到: ${itemName}`);
        });
    });
}

// 初始化视频播放器
function initVideoPlayer() {
    const videoContent = document.querySelector('.video-content');
    const playButton = document.querySelector('.play-center-button');
    
    if (videoContent && playButton) {
        videoContent.addEventListener('click', function() {
            // 隐藏播放按钮
            playButton.style.opacity = '0';
            playButton.style.transform = 'translate(-50%, -50%) scale(0.8)';
            
            // 模拟视频播放
            setTimeout(() => {
                alert('视频开始播放（这是演示页面）');
                // 恢复播放按钮
                playButton.style.opacity = '0.9';
                playButton.style.transform = 'translate(-50%, -50%) scale(1)';
            }, 300);
        });
    }
    
    // 进度条点击
    const progressBar = document.querySelector('.progress-bar');
    if (progressBar) {
        progressBar.addEventListener('click', function(e) {
            const rect = this.getBoundingClientRect();
            const percent = (e.clientX - rect.left) / rect.width;
            const progressFill = this.querySelector('.progress-fill');
            if (progressFill) {
                progressFill.style.width = (percent * 100) + '%';
            }
        });
    }
}

// 初始化操作按钮
function initActionButtons() {
    const actionButtons = document.querySelectorAll('.action-btn');
    
    actionButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            
            const numberElement = this.querySelector('.action-number');
            if (numberElement) {
                const currentCount = numberElement.textContent;
                const count = parseCount(currentCount);
                
                // 切换激活状态
                const isActive = this.classList.contains('active');
                
                if (isActive) {
                    this.classList.remove('active');
                    numberElement.textContent = formatCount(count - 1);
                } else {
                    this.classList.add('active');
                    numberElement.textContent = formatCount(count + 1);
                    
                    // 添加动画效果
                    this.style.transform = 'scale(1.2)';
                    setTimeout(() => {
                        this.style.transform = 'scale(1)';
                    }, 200);
                }
            }
        });
    });
}

// 初始化关注按钮
function initFollowButton() {
    const followBtn = document.querySelector('.follow-btn-small');
    
    if (followBtn) {
        followBtn.addEventListener('click', function(e) {
            e.stopPropagation();
            
            const isFollowing = this.textContent === '✓';
            
            if (isFollowing) {
                this.innerHTML = '<span>+</span>';
                this.style.backgroundColor = '#FF0050';
            } else {
                this.innerHTML = '<span>✓</span>';
                this.style.backgroundColor = '#4CAF50';
                
                // 添加动画效果
                this.style.transform = 'scale(1.2)';
                setTimeout(() => {
                    this.style.transform = 'scale(1)';
                }, 200);
            }
        });
    }
}

// 初始化视频控制按钮
function initVideoControls() {
    const controlButtons = document.querySelectorAll('.control-btn, .control-btn-icon');
    
    controlButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            const title = this.getAttribute('title') || this.textContent.trim();
            if (title) {
                console.log(`点击: ${title}`);
            }
        });
    });
    
    // 弹幕按钮
    const danmuBtn = document.querySelector('.control-btn');
    if (danmuBtn) {
        danmuBtn.addEventListener('click', function() {
            alert('弹幕功能（需要登录）');
        });
    }
}

// 解析计数文本（如 "20.3万" -> 203000）
function parseCount(countText) {
    const text = countText.toLowerCase().trim();
    
    if (text.endsWith('万')) {
        return Math.floor(parseFloat(text) * 10000);
    } else if (text.endsWith('k')) {
        return Math.floor(parseFloat(text) * 1000);
    } else {
        return parseInt(text.replace(/[^\d]/g, '')) || 0;
    }
}

// 格式化计数（如 203000 -> "20.3万"）
function formatCount(count) {
    if (count >= 10000) {
        return (count / 10000).toFixed(1) + '万';
    } else if (count >= 1000) {
        return (count / 1000).toFixed(1) + 'k';
    } else {
        return count.toString();
    }
}

// 登录按钮点击事件
const loginBtn = document.querySelector('.login-btn');
if (loginBtn) {
    loginBtn.addEventListener('click', function() {
        alert('打开登录页面（这是演示页面）');
    });
}

// 用户头像点击事件
const userAvatar = document.querySelector('.user-avatar');
if (userAvatar) {
    userAvatar.addEventListener('click', function() {
        alert('打开用户中心（这是演示页面）');
    });
}

// 顶部链接点击事件
document.querySelectorAll('.header-link').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        const text = this.textContent.trim();
        alert(`${text}功能（这是演示页面）`);
    });
});

// 添加滚动时的视觉效果
let lastScrollTop = 0;
window.addEventListener('scroll', function() {
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    const header = document.querySelector('.header');
    
    if (scrollTop > lastScrollTop && scrollTop > 100) {
        // 向下滚动
        header.style.transform = 'translateY(-100%)';
    } else {
        // 向上滚动
        header.style.transform = 'translateY(0)';
    }
    
    lastScrollTop = scrollTop;
});

// 视频标题点击事件
const authorTag = document.querySelector('.author-tag');
if (authorTag) {
    authorTag.addEventListener('click', function() {
        alert('打开创作者主页（这是演示页面）');
    });
}
