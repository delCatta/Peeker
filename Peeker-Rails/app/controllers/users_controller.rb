# frozen_string_literal: true

class UsersController < ApplicationController
  def me
    @user = Current.user
  end
end
