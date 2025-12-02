Page({
    data: {
      newTodoText: '',
      searchText: '',
      currentFilter: 'all',
      filterOptions: [
        { key: 'all', text: '全部' },
        { key: 'active', text: '未完成' },
        { key: 'completed', text: '已完成' }
      ],
      todos: [
      ],
      filteredTodos: [],
      totalCount: 0,
      completedCount: 0,
      loading: false
    },
  
    onLoad() {
      // 模拟加载数据
      this.setData({ loading: true });
      setTimeout(() => {
        this.updateFilteredTodos();
        this.updateStats();
        this.setData({ loading: false });
      }, 500);
    },
  
    onInputChange(e) {
      this.setData({
        newTodoText: e.detail.value
      });
    },
  
    onSearchChange(e) {
      this.setData({
        searchText: e.detail.value
      }, () => {
        this.updateFilteredTodos();
      });
    },
  
    addTodo() {
      const text = this.data.newTodoText.trim();
      if (!text) {
        wx.showToast({
          title: '请输入待办事项',
          icon: 'none'
        });
        return;
      }
  
      const newTodo = {
        id: Date.now(),
        text: text,
        completed: false,
        date: this.formatDate(new Date())
      };
  
      this.setData({
        todos: [newTodo, ...this.data.todos],
        newTodoText: ''
      }, () => {
        this.updateFilteredTodos();
        this.updateStats();
        wx.showToast({
          title: '添加成功',
          icon: 'success'
        });
      });
    },
  
    toggleTodo(e) {
      const id = e.currentTarget.dataset.id;
      const todos = this.data.todos.map(todo => {
        if (todo.id === id) {
          return { ...todo, completed: !todo.completed };
        }
        return todo;
      });
  
      this.setData({ todos }, () => {
        this.updateFilteredTodos();
        this.updateStats();
      });
    },
  
    deleteTodo(e) {
      const id = e.currentTarget.dataset.id;
      
      wx.showModal({
        title: '确认删除',
        content: '确定要删除这个待办事项吗？',
        success: (res) => {
          if (res.confirm) {
            const todos = this.data.todos.filter(todo => todo.id !== id);
            this.setData({ todos }, () => {
              this.updateFilteredTodos();
              this.updateStats();
              wx.showToast({
                title: '删除成功',
                icon: 'success'
              });
            });
          }
        }
      });
    },
  
    changeFilter(e) {
      const key = e.currentTarget.dataset.key;
      this.setData({
        currentFilter: key
      }, () => {
        this.updateFilteredTodos();
      });
    },
  
    updateFilteredTodos() {
      const { todos, currentFilter, searchText } = this.data;
      let filteredTodos = todos.filter(todo => {
        const matchesFilter = 
          currentFilter === 'all' || 
          (currentFilter === 'active' && !todo.completed) ||
          (currentFilter === 'completed' && todo.completed);
        
        const matchesSearch = todo.text.toLowerCase().includes(searchText.toLowerCase());
        
        return matchesFilter && matchesSearch;
      });
  
      this.setData({ filteredTodos });
    },
  
    updateStats() {
      const total = this.data.todos.length;
      const completed = this.data.todos.filter(todo => todo.completed).length;
      this.setData({
        totalCount: total,
        completedCount: completed
      });
    },
  
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },
  
    onShareAppMessage() {
      return {
        title: '我的待办事项',
        path: '/pages/index/index'
      };
    }
  });