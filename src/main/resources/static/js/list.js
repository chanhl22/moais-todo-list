new Vue({
    el: '#wrap',
    data: {
        todolist: [],
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            let $this = this;

            axios.get('/todo/list?page=0&size=20')
                .then(function (response) {
                    $this.todolist = response.data.data.todos;
                })
                .catch(function (error) {
                    alert("오류발생");
                });
        },

        update: function (id, content, status) {
            window.location.href = '/update?id=' + id + '&content=' + content + '&status=' + status;
        }

    }
})