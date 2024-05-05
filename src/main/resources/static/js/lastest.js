new Vue({
    el: '#wrap',
    data: {
        id: '',
        content: '',
        status: '',
        nickname: '',
        createdDateTime: '',
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            let $this = this;

            axios.get('/todo/latest')
                .then(function (response) {
                    $this.id = response.data.data.id;
                    $this.content = response.data.data.content;
                    $this.status = response.data.data.status;
                    $this.nickname = response.data.data.nickname;
                    $this.createdDateTime = response.data.data.createdDateTime;
                })
                .catch(function (error) {
                    alert("오류발생");
                });
        },

    }
})