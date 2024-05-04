new Vue({
    el: '#wrap',
    data: {
    },
    methods: {
        bye: function () {
            axios.delete('account/delete')
                .then(function (response) {
                    alert('탈퇴 되었습니다.');
                    window.location.href = '/logout';
                })
                .catch(function (error) {
                    alert('오류 발생');
                });
        },
    }
})