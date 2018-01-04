var app = {};

//--------------
// Format date tamplate
//--------------
_.template.formatdatevalue = function (stamp) {
     var isoStr = new Date(stamp).toISOString();
     var fragments = isoStr.split("T");
     return fragments[0];
 };

//--------------
// Routes
//--------------
app.Router = Backbone.Router.extend({
  routes: {
    '*filter' : 'setFilter'
  },
  setFilter: function(params) {
    window.filter = params.trim() || '';
    app.moviesList.trigger('reset');
  }
});

//--------------
// Model & Collection
//--------------
app.Model = Backbone.Model.extend(
{
   urlRoot: 'http://localhost:8080/api/movies',
   idAttribute: "_id"
});

app.MoviesCollection = Backbone.Collection.extend({
  url:'http://localhost:8080/api/movies',
  model: app.Model,
  watched: function() {
    return this.filter(function( movie ) {
      return movie.get('watched');
    });
  },
  remaining: function() {
    return this.without.apply( this, this.watched() );
  }
});

//Initialize collection
app.moviesList = new app.MoviesCollection();

//--------------
// Renders individual todo items
// Individual itens actions
//--------------
app.MoviesView = Backbone.View.extend({
  tagName: 'tr',
  template: _.template($('#item-template').html()),
  render: function(){
    this.$el.html(this.template(this.model.toJSON()));
    this.input = this.$('.edit');
    this.inputDate = this.$('.editDate');
    this.watched = this.$('.toggle');
    return this; // enable chained calls
  },
  initialize: function(){
      this.model.on('change', this.render, this);
      // remove: Convenience Backbone's function for removing the view from the DOM.
      this.model.on('destroy', this.remove, this);
  },
  events: {
    'dblclick #titleLabel' : 'edit',
    'keypress .edit' : 'updateOnEnter',
    'blur .edit' : 'close',
    'dblclick #dateLabel' : 'editDate',
    'keypress .editDate' : 'updateDateOnEnter',
    'blur #dateLabel' : 'closeDateEdit',
    'click .toggle': 'toggleCompleted',
    'click .destroy': 'destroy'
  },
  edit: function(){
    this.$el.addClass('editing');
    this.input.focus();
  },
  close: function(){
    var value = this.input.val().trim();
    if(value) {
      this.model.save({title: value});
    }
    this.$el.removeClass('editing');
  },
  updateOnEnter: function(e){
    if(e.which == 13){
      this.close();
    }
  },
  editDate: function(){
    this.$el.addClass('editing');
    this.inputDate.focus();
  },
  closeDateEdit: function(){
    var value = this.inputDate.val().trim();
    if(value) {
      this.model.save({releaseDate: value});
    }
    this.$el.removeClass('editing');
  },
  updateDateOnEnter: function(e){
    if(e.which == 13){
      this.closeDateEdit();
    }
  },
  toggleCompleted: function(){
    var value = !this.model.get("watched");
    this.model.save({watched: value});
  },
  destroy: function(){
    this.model.destroy();
  }
});

//--------------
// Add to list
//--------------
app.AppView = Backbone.View.extend({
  el: '#moviesapp',
  initialize: function () {
    this.inputname = this.$('#new-movie-name');
    this.inputdate = this.$('#new-movie-date');
    app.moviesList.on('add', this.addOne, this);
    app.moviesList.on('reset', this.addAll, this);
    app.moviesList.fetch();
  },
  events: {
    'click #add-movie': 'createMovie'
  },
  createMovie: function(e){
    app.moviesList.create(this.newAttributes());
    this.inputname.val(''); // clean input box
    this.inputdate.val(''); // clean input box
  },
  addOne: function(movie){
    var view = new app.MoviesView({model: movie});
    $('#movies-list').append(view.render().el);
  },
  addAll: function(){
    this.$('#movies-list').html(''); // clean the todo list
      // filter todo item list
      switch(window.filter){
        case 'pending':
          _.each(app.moviesList.remaining(), this.addOne);
          break;
        case 'watched':
          _.each(app.moviesList.watched(), this.addOne);
          break;
        default:
          app.moviesList.each(this.addOne, this);
          break;
      }
  },
  newAttributes: function(){
    return {
      title: this.inputname.val().trim(),
      releaseDate: this.inputdate.val().trim(),
      watched: false
    }
  }
});

//--------------
// Initializers
//--------------
app.router = new app.Router();
Backbone.history.start();
app.appView = new app.AppView();
