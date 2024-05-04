new Vue({
    el: '#wrap',
    data: {
        username: '',
        password: ''
    },
    methods: {
        signup: function () {
            let $this = this;

            let requestData = {
                username: $this.username,
                password: $this.password
            }
            let requestJson = JSON.stringify(requestData);

            axios.post('/account/signup', requestJson, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(function (response) {
                    alert("등록 완료");
                    window.location.href = '/';
                })
                .catch(function (error) {
                    alert("오류 발생");
                });
        },
    }
})