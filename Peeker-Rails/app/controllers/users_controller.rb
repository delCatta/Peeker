class UsersController < ApplicationController
  def me
    @user = Current.user
  end
end
