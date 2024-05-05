new Vue({
    el: '#wrap',
    data: {
        content: '',
        status: ''
    },
    methods: {
        save: function () {
            let $this = this;

            let requestData = {
                content: $this.content,
                status: $this.status
            }
            let requestJson = JSON.stringify(requestData);

            axios.post('/todo/save', requestJson, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(function (response) {
                    alert("등록 완료");
                    window.location.href = '/list';
                })
                .catch(function (error) {
                    alert(error.response.data.message);
                });
        },
    }
})