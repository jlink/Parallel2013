- module(shelves).
- export([new_shelf/1, put_in/2, products/1, capacity/1]).
%% - export([ call / 1 , create_shelf / 1]) .

new_shelf(Capacity) -> {Capacity, []}.

put_in(Shelf, Product) ->
  [Product | Shelf].

products({_, Products}) ->
  Products.

capacity({Capacity, _}) ->
  Capacity.