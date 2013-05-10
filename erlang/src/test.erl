%% Copyright
-module(test).
-author("link").

-include_lib("eunit/include/eunit.hrl").

storehouse_test() ->
  storehouse:start(),
  Shelf = storehouse:create_shelf(bookshelf),
  io:format("Shelf: ~w~n", [Shelf]),

  ?assert(Shelf =:= []).

single_shelf_test() ->
  Shelf = shelves:new_shelf(3),
  ?assert(shelves:products(Shelf) =:= []),
  ?assert(shelves:capacity(Shelf) =:= 3).
