new Vue({
    el: '#wrap',
    data: {
        id: '',
        content: '',
        status: ''
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            let $this = this;

            let urlStr = window.location.href;
            let url = new URL(urlStr);
            let urlParams = url.searchParams;

            $this.id = urlParams.get('id');
            $this.content = urlParams.get('content');
            $this.status = urlParams.get('status');
        },

        update: function () {
            let $this = this;

            let requestData = {
                id: $this.id,
                content: $this.content,
                status: $this.status
            }
            let requestJson = JSON.stringify(requestData);

            axios.post('/todo/update', requestJson, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(function (response) {
                    alert("수정 완료");
                    window.location.href = '/list';
                })
                .catch(function (error) {
                    alert("오류 발생");
                });
        },
    }
})