
function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}


var postApi = Vue.resource('/post{/id}');

Vue.component('post-form', {
    props: ['post', 'postAttr'],
    data: function() {
        return {
            text: '',
            id: ''
        }
    },
    watch: {
        postAttr: function(newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Write something" v-model="text" />' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var post = { text: this.text };

            if (this.id) {
                postApi.update({id: this.id}, post).then(result =>
                result.json().then(data => {
                    var index = getIndex(this.post, data.id);
                this.post.splice(index, 1, data);
                this.text = ''
                this.id = ''
            })
            )
            } else {
                postApi.save({}, post).then(result =>
                result.json().then(data => {
                    this.post.push(data);
                this.text = ''
            })
            )
            }
        }
    }
});

Vue.component('post-row', {
    props: ['post', 'editMethod', 'posts'],
    template: '<div>' +
        'MongoId-<i>|{{ post.id }}|</i> {{ post.text }}' +
        '<span style="position: absolute; right: 0">' +
        ' {{post.creationDate}}' +
        '<span style="position: sticky; right: 0">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.post);
        },
        del: function() {
            postApi.remove({id: this.post.id}).then(result => {
                if (result.ok) {
                this.post.splice(this.post.indexOf(this.post), 1)
            }
        })
        }
    }
});

Vue.component('posts-list', {
    props: ['posts'],
    data: function() {
        return {
            post: null
        }
    },
    template:
        '<div style="position: relative; width: 700px;">' +
        '<post-form :posts="posts" :postAttr="post" />' +
        '<post-row v-for="post in posts" :key="post.id" :post="post" ' +
        ':editMethod="editMethod" :posts="posts" />' +
        '</div>',
    created: function() {
        postApi.get().then(result =>
        result.json().then(data =>
        data.forEach(post => this.posts.push(post))
    )
    )
    },
    methods: {
        editMethod: function(post) {
            this.post = post;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<posts-list :posts="posts" />',
    data: {
        posts: []
    }
});