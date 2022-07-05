# frozen_string_literal: true

class UsersController < ApplicationController
  before_action :set_user
  
  def me
  end

  def update
    if @user.update(user_params)
      render :me, status: :ok, location: users_me_url
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  private
    def set_user
      @user = Current.user
    end
    
    def user_params
      params.require(:user).permit(:notifications_enabled, :days_about_to_expire)
    end
end
