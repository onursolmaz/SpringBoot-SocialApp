import React, {useEffect, useState} from 'react';
import ProfilePictureWithDefault from "./ProfilePictureWithDefault";
import {Link} from "react-router-dom";
import {format} from "timeago.js";
import {useSelector} from "react-redux";
import {deleteLike, deleteTweet, like} from "../Api/ApiCalls";
import Modal from "./Modal";
import {useApiProgress} from "../Shared/ApiProgress";

const TweetView = (props) => {
    const loggedInUser = useSelector( store => store.username )
    const {tweet, onDeleteTweet} = props;
    const {user, content, date, fileAttachment, id, likeCount, iliked} = tweet
    const {username, displayName, image} = user
    const [modalVisible, setModalVisible] = useState( false );
    const [likeCountUI, setLikecountUI] = useState( likeCount );
    const [isliked, setIsLiked] = useState( iliked );

    useEffect( () => {

    }, [likeCountUI] )

    const pendingApiCall = useApiProgress( "delete", "/api/tweets/" + id, true );

    const isOwned = loggedInUser === username;

    const onClikdelete = async () => {
        await deleteTweet( id );
        onDeleteTweet( id );
    }
    const onClickCancel = () => {
        setModalVisible( false )
    }


    const onClickLike = async () => {
        if (!isliked) {
            await like( id );
            setLikecountUI( likeCountUI + 1 );
            setIsLiked( true );
        } else {
            await deleteLike( id )
            setLikecountUI( likeCountUI - 1 )
            setIsLiked( false );
        }
    }

    const formetted = format( date )
    return (<>
            <div className="card mb-2">
                <div className="d-flex">
                    <ProfilePictureWithDefault image={image} width="52" height="52" className="rounded-circle p-1"/>
                    <div className="flex-fill mt-2 pl-2">
                        <Link to={`/users/${username}`} className="text-decoration-none text-dark">
                            <span className="font-weight-bolder">{displayName} @{username}</span>
                            <span className="text-muted font-weight-light"> · {formetted}</span>
                        </Link>
                    </div>
                    {isOwned && <button className="btn btn-sm btn-delete-link" onClick={() => setModalVisible( true )}>
                        <i className="material-icons">delete_outline</i>
                    </button>}
                </div>
                <div className="pl-3 mt-1">{content}</div>
                {fileAttachment && (
                    <div className="pl-3">
                        <img className="img-fluid" src={"images/attachments/" + fileAttachment.name} alt="--"/>
                    </div>
                )}
                <div className="row text-center m-2">
                    <div className="col">
                        <button className="btn btn-sm btn-like-link d-inline-flex" disabled={!loggedInUser}>
                            <i className="material-icons icon-hover"
                               style={{hover: "none"}}>comment</i>
                            <span className="ml-1 text-black-50"></span>
                        </button>
                    </div>
                    <div className="col">
                        <button className="btn btn-sm btn-like-link d-inline-flex" disabled={!loggedInUser}>
                            <i className="material-icons icon-hover"
                               style={{hover: "none"}}>swap_vert</i>
                            <span className="ml-1 text-black-50"></span>
                        </button>
                    </div>
                    <div className="col">
                        <button className="btn btn-sm btn-like-link d-inline-flex" disabled={!loggedInUser}
                                onClick={onClickLike}>
                            <i className="material-icons icon-hover"
                               style={{color: isliked && loggedInUser ? "red" : "", hover: "none"}}>favorite</i>
                            <span className="ml-1 text-black-50">{likeCountUI} </span>
                        </button>
                    </div>
                </div>
            </div>

            <Modal visible={modalVisible} onClickCancel={onClickCancel} onClickOk={onClikdelete}
                   pendingApiCall={pendingApiCall}
                   message={
                       <div>
                           <div><strong>Are you sure to delete tweet?</strong></div>
                           <span>{content}</span>
                       </div>
                   } modalTittle={"Delete Tweet"}/>
        </>
    );
};

export default TweetView;