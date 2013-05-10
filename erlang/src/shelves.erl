- module(shelves).
- export([new_shelf/1, products/1, capacity/1]).
- export([put_in/2, take_out/2]).
%% - export([ call / 1 , create_shelf / 1]) .

new_shelf(Capacity) -> {Capacity, []}.

put_in(Shelf, Product) ->
  {Capacity, Products} = Shelf,
  if
    Capacity =:= length(Products) -> throw({over_capacity});
    true -> {Capacity, [Product | Products]}
  end.

take_out(Shelf, Product) ->
  {Capacity, Products} = Shelf,
  {Capacity, lists:delete(Product, Products)}.

products({_, Products}) ->
  Products.

capacity({Capacity, _}) ->
  Capacity.