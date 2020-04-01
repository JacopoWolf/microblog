"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
/**
 * manages access to the REST api at /rest/
 * completely asynchronous
 */
var ApiAccess = /** @class */ (function () {
    function ApiAccess() {
    }
    // GET methods
    ApiAccess.prototype.getPage = function (state) {
        return __awaiter(this, void 0, void 0, function () {
            var query;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        query = '/rest/posts' +
                            '?count=' + state.pageSize +
                            '&page=' + state.pageNumber +
                            '&limitcontent=' + state.contentMaxLengthInView;
                        return [4 /*yield*/, $.get(query)];
                    case 1: return [2 /*return*/, _a.sent()];
                }
            });
        });
    };
    ApiAccess.prototype.getPost = function (postId) {
        return __awaiter(this, void 0, void 0, function () {
            var query;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        query = '/rest/posts/' + postId;
                        return [4 /*yield*/, $.get(query)];
                    case 1: return [2 /*return*/, _a.sent()];
                }
            });
        });
    };
    ApiAccess.prototype.getPostComments = function (postId) {
        return __awaiter(this, void 0, void 0, function () {
            var query;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        query = '/rest/posts/' + postId + '/comments';
                        return [4 /*yield*/, $.get(query)];
                    case 1: return [2 /*return*/, _a.sent()];
                }
            });
        });
    };
    // POST methods
    //? those might use a callback for success or failure
    ApiAccess.prototype.submitUser = function (newUser) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, $.ajax({
                            url: "/rest/users",
                            type: "POST",
                            data: JSON.stringify({ username: newUser.username, email: newUser.email }),
                            contentType: "application/json",
                        })];
                    case 1: return [2 /*return*/, _a.sent()];
                }
            });
        });
    };
    ApiAccess.prototype.submitPost = function (newPost) {
        var _a;
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, $.ajax({
                            url: "/rest/posts?from=" + ((_a = newPost.author) === null || _a === void 0 ? void 0 : _a.username),
                            type: "POST",
                            data: JSON.stringify({ title: newPost.title, content: newPost.content }),
                            contentType: "application/json",
                        })];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    };
    ApiAccess.prototype.submitComment = function (from, underPostId, content) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                return [2 /*return*/];
            });
        });
    };
    return ApiAccess;
}());
var App = /** @class */ (function () {
    function App() {
        var _this = this;
        // component initialization
        this._state = new ApplicationState();
        this._api = new ApiAccess();
        this._view = new View(this._state, this._api);
        // initializing view
        //* already loads with current state
        this._view.currentLocation = "view";
        this.updateView();
        //      EVENT BINDING
        // previous page, can't get below 0
        $('#btn_back').click(function () { if (--_this._state.pageNumber < 0)
            _this._state.pageNumber = 0; _this.updateView(); });
        // next page
        $('#btn_forw').click(function () { ++_this._state.pageNumber; _this.updateView(); });
        // return to first page
        $('#pagespan').click(function () { _this._state.pageNumber = 0; _this.updateView(); });
        // refresh
        $('#pagecount').click(function () { _this.updateView(); });
        // post creation mode
        $('#btn_create').click(function () { _this._view.currentLocation = "create"; });
        // get back to viewing posts
        $('#btn_nocreate').click(function () { _this._view.currentLocation = "view"; _this.updateView(); });
        // submits a new user and post
        $('#btn_createpost').click(function () { return __awaiter(_this, void 0, void 0, function () { return __generator(this, function (_a) {
            this.submitUserAndPost();
            return [2 /*return*/];
        }); }); });
    }
    App.prototype.updateView = function () {
        var _this = this;
        $.when(this._api
            .getPage(this._state)
            .then(function (posts) { _this._view.displayMany(posts); }));
    };
    App.prototype.submitUserAndPost = function () {
        return __awaiter(this, void 0, void 0, function () {
            var user, _a, post;
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0:
                        user = {
                            username: $('#c_username').val(),
                            email: $('#c_email').val()
                        };
                        _b.label = 1;
                    case 1:
                        _b.trys.push([1, 3, , 4]);
                        return [4 /*yield*/, this._api.submitUser(user)];
                    case 2:
                        _b.sent();
                        alert('creato un novo utente!');
                        return [3 /*break*/, 4];
                    case 3:
                        _a = _b.sent();
                        console.log('utente esistente');
                        return [3 /*break*/, 4];
                    case 4:
                        post = {
                            title: $('#c_title').val(),
                            content: $('#c_content').val(),
                            author: user,
                            // assigned by the Server
                            date: undefined,
                            id: undefined
                        };
                        return [4 /*yield*/, this._api.submitPost(post)];
                    case 5:
                        _b.sent();
                        alert('creato un nuovo post!');
                        return [2 /*return*/];
                }
            });
        });
    };
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
        this.currentLocation = 'create';
        this.lastPostId = -1;
    }
    return ApplicationState;
}());
var View = /** @class */ (function () {
    function View(state, access) {
        this._state = state;
        this._api = access;
    }
    Object.defineProperty(View.prototype, "currentLocation", {
        //* changes between post view and post editor
        get: function () {
            return this._state.currentLocation;
        },
        set: function (value) {
            switch (value) {
                case 'view':
                    $('#postcontainer').css('display', 'block');
                    $('#postcreator').css('display', 'none');
                    break;
                case 'create':
                    $('#postcontainer').css('display', 'none');
                    $('#postcreator').css('display', 'block');
                    break;
            }
            this._state.currentLocation = value;
        },
        enumerable: true,
        configurable: true
    });
    //  DISPLAY FUNCTIONS
    View.prototype.displayMany = function (posts) {
        var _this = this;
        this.clear();
        // updates page number in the navbar
        $('#pagecount').html(this._state.pageNumber.toString());
        // displays every post
        $.each(posts, function (i, post) {
            var _a;
            // clones the template
            var pt = $('#templates').find('.post').clone();
            // header
            pt.find('.postheader').html('<br>' + ((_a = post.author) === null || _a === void 0 ? void 0 : _a.username) + '</b> on ' + post.date);
            // title
            pt.find('.postcontent_h').html(post.title);
            pt.find('.postcontent_h').click(
            //* used to load a single post
            function () { return __awaiter(_this, void 0, void 0, function () {
                var _a, _b;
                return __generator(this, function (_c) {
                    switch (_c.label) {
                        case 0:
                            _a = this.displaySingle;
                            return [4 /*yield*/, this._api.getPost(post.id)];
                        case 1:
                            _a.apply(this, [_c.sent()]);
                            _b = this.displayComments;
                            return [4 /*yield*/, this._api.getPostComments(post.id)];
                        case 2:
                            _b.apply(this, [_c.sent()]);
                            return [2 /*return*/];
                    }
                });
            }); });
            // content
            var contentEnd = (post.content.length < _this._state.contentMaxLengthInView) ? '' : '...';
            pt.find('.postcontent_p').html(post.content + contentEnd);
            $('#postcontainer').append(pt);
        });
    };
    View.prototype.displaySingle = function (post) {
        var _a, _b;
        this.clear();
        //updates page count
        $('#pagecount').html('<i><b>(' + this._state.pageNumber + ')</b></i>');
        // clones the template
        var pt = $('#templates').find('.singlepost').clone();
        // header
        pt.find('.singlepost_h').html((_a = post.date) === null || _a === void 0 ? void 0 : _a.toString());
        pt.find('.singlepostheader_usrn').html((_b = post.author) === null || _b === void 0 ? void 0 : _b.username);
        // title
        pt.find('.singlepostheader_t').html(post.title);
        // content
        pt.find('.singlepostcontent_p').html(post.content);
        $('#postcontainer').append(pt);
        this._state.lastPostId = post.id;
    };
    View.prototype.displayComments = function (comments) {
        $.each(comments, function (i, comment) {
            var _a;
            var cmt = $('#templates').find('.comment').clone();
            cmt.find('.postheader').html('<b>' + ((_a = comment.author) === null || _a === void 0 ? void 0 : _a.username) + '</b> on ' + comment.date);
            cmt.find('.postcontent').html(comment.content);
            $('#postcontainer').append(cmt);
        });
    };
    // utilities
    View.prototype.clear = function () {
        $('#postcontainer').html('');
    };
    return View;
}());
var app;
$(document).ready(function () {
    app = new App();
});