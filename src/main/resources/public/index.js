"use strict";
/**
 * manages access to the REST api at /rest/
 */
var ApiAccess = /** @class */ (function () {
    function ApiAccess(state) {
        this.state = state;
    }
    ApiAccess.prototype.getPage = function (handler) {
        var query = '/rest/posts' +
            '?count=' + this.state.pageSize +
            '&page=' + this.state.pageNumber +
            '&limitcontent=' + this.state.contentMaxLengthInView;
        $.get(query, handler);
    };
    ApiAccess.prototype.getPost = function (postId, handler) {
        var query = '/rest/posts/' + postId;
        $.get(query, handler);
    };
    ApiAccess.prototype.submitPost = function (newPost, handler) {
        $.ajax({
            url: "/rest/posts?from=" + newPost.author.username,
            type: "POST",
            data: JSON.stringify({ title: newPost.title, content: newPost.content }),
            contentType: "application/json",
            success: handler
        });
    };
    ApiAccess.prototype.submitComment = function (from, underPostId, content, handler) {
        //todo implement ajax call
    };
    return ApiAccess;
}());
/**
 * represents a single post
 */
var Post = /** @class */ (function () {
    function Post(title, date, content, author, id) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.author = author;
    }
    Post.prototype.toString = function () {
        return '[' + this.id;
    };
    return Post;
}());
/**
 * represents the information of a single user
 */
var User = /** @class */ (function () {
    function User(username, email) {
        this.username = username;
        this.email = email;
    }
    return User;
}());
var App = /** @class */ (function () {
    function App() {
        this.state = new ApplicationState();
        this.apiAccess = new ApiAccess(this.state);
        this.state.currentLocation = "view";
        //todo complete editing
        this.apiAccess.getPage(function (data) {
            $.each(data, function (i, val) { return alert(val.id + ' ' + val.date); });
            //! this is still a test
        });
    }
    return App;
}());
/**
 * represents the application state
 */
var ApplicationState = /** @class */ (function () {
    function ApplicationState() {
        this.pageSize = 10;
        this.pageNumber = 0;
        this.contentMaxLengthInView = 40;
        this._currentLocation = 'create';
        this.lastPostId = -1;
    }
    Object.defineProperty(ApplicationState.prototype, "currentLocation", {
        get: function () {
            return this._currentLocation;
        },
        set: function (value) {
            switch (value) {
                case 'view':
                    //todo add view stuff
                    break;
                case 'create':
                    //todo add create stuff
                    break;
            }
            this._currentLocation = value;
        },
        enumerable: true,
        configurable: true
    });
    return ApplicationState;
}());
var app;
$(document).ready(function () {
    app = new App();
});
