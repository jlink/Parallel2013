- module(shelves).
- export([new_shelf/1, products/1, capacity/1]).
- export([put_in/2, take_out/2]).
- export([start_new_shelf/1, loop/1]).
%% - export([ call / 1 , create_shelf / 1]) .

start_new_shelf(Capacity) ->
  Shelf = new_shelf(Capacity),
  spawn(shelves, loop, [Shelf]).

loop(Shelf) ->
  receive
    {From, {put_in, Product}} ->
      NewShelf = put_in(Shelf, Product),
      reply(From, products(NewShelf)),
      loop(NewShelf);
    {From, stop} ->
      reply(From, ok);
    {From, Msg} ->
      io:format("unknown message: ~w~n", [Msg]),
      reply(From, unknown),
      loop(Shelf)
  end.

reply(Pid, Reply) ->
  Pid ! {self(), Reply}.

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